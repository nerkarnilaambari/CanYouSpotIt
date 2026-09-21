package com.example.canyouspotit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.UserPreferences
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    private lateinit var imgPrivacyLock: ImageView
    private val userPreferencesDao by lazy { AppDatabase.getDatabase(this).userPreferencesDao() }

    // The result Intent's data_collection_enabled value drives the privacy chip's alpha.
    private val settingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val enabled = result.data?.getBooleanExtra(
                SettingsActivity.EXTRA_DATA_COLLECTION_ENABLED, false
            ) ?: false
            Log.d(TAG, "Settings result received: data_collection_enabled=$enabled")
            applyPrivacyIconState(enabled)
        } else {
            Log.d(TAG, "Settings result ignored: resultCode=${result.resultCode}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        imgPrivacyLock = findViewById(R.id.imgPrivacyLock)
        // Cold start: the chip starts from the stored flag, before any Settings round-trip.
        applyPrivacyIconState(isDataCollectionEnabled())

        findViewById<android.view.View>(R.id.btnGoToScanner).setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnGoToLearn).setOnClickListener {
            startActivity(Intent(this, LearnActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnGoToSettings).setOnClickListener {
            settingsLauncher.launch(Intent(this, SettingsActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        retryRegionDetectionIfNeeded()
    }

    // Full-opacity lock = saving on, dimmed lock = saving off.
    private fun applyPrivacyIconState(enabled: Boolean) {
        imgPrivacyLock.alpha = if (enabled) 1f else 0.45f
        Log.d(TAG, "Privacy chip alpha set to ${imgPrivacyLock.alpha} (enabled=$enabled)")
    }

    // Silent per-launch attempt; never prompts, leaves region_resolved false to retry later.
    private fun retryRegionDetectionIfNeeded() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val consentGiven = prefs.getBoolean("consent_given", false)
        val dataCollectionEnabled = prefs.getBoolean("data_collection_enabled", false)
        val regionResolved = prefs.getBoolean("region_resolved", true)
        if (!consentGiven || !dataCollectionEnabled || regionResolved) return

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) return

        if (!retryInProgress.compareAndSet(false, true)) return

        val cancellationTokenSource = CancellationTokenSource()
        val timeoutHandler = Handler(Looper.getMainLooper())
        val timeoutRunnable = Runnable { cancellationTokenSource.cancel() }
        timeoutHandler.postDelayed(timeoutRunnable, RETRY_TIMEOUT_MS)

        LocationServices.getFusedLocationProviderClient(this)
            .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cancellationTokenSource.token)
            .addOnSuccessListener { location ->
                timeoutHandler.removeCallbacks(timeoutRunnable)
                if (location != null) {
                    val region = classifyRegion(location.latitude, location.longitude)
                    prefs.edit()
                        .putString("region", region)
                        .putBoolean("region_resolved", true)
                        .apply()
                    lifecycleScope.launch {
                        userPreferencesDao.insert(
                            UserPreferences(consentGiven = true, detectedRegion = region)
                        )
                    }
                }
                retryInProgress.set(false)
            }
            .addOnFailureListener {
                timeoutHandler.removeCallbacks(timeoutRunnable)
                retryInProgress.set(false)
            }
    }

    companion object {
        private const val TAG = "HomeActivity"
        private const val RETRY_TIMEOUT_MS = 10_000L
        private val retryInProgress = AtomicBoolean(false)
    }
}
