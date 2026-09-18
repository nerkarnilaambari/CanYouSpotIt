package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class HomeActivity : BaseActivity() {

    private lateinit var imgPrivacyLock: ImageView

    // SettingsActivity returns data_collection_enabled in its result Intent whenever it
    // finishes; the privacy chip's glyph alpha follows that value.
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

    // Full-opacity lock = saving on, dimmed lock = saving off.
    private fun applyPrivacyIconState(enabled: Boolean) {
        imgPrivacyLock.alpha = if (enabled) 1f else 0.45f
        Log.d(TAG, "Privacy chip alpha set to ${imgPrivacyLock.alpha} (enabled=$enabled)")
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}
