package com.example.canyouspotit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.UserPreferences
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class ConsentActivity : BaseActivity() {

    private val userPreferencesDao by lazy { AppDatabase.getDatabase(this).userPreferencesDao() }

    // must be registered before onStart, so this is a property, not something created inside the click listener
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) detectRegionAndSave() else saveConsentAndRegion("GLOBAL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent)

        findViewById<Button>(R.id.btnAgree).setOnClickListener {
            // consent_given = "the consent question has been answered" (MainActivity's startup
            // gate); data_collection_enabled = "...and the answer was yes, save my history".
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("consent_given", true)
                .putBoolean("data_collection_enabled", true)
                .apply()

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {
                detectRegionAndSave()
            } else {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }

        findViewById<Button>(R.id.btnDecline).setOnClickListener {
            // The user answered the consent question (so the startup gate must pass and this
            // screen must not reappear) - they just answered "no" to saving history. No Room
            // write here; the data_collection_enabled = false flag is what gates all writes.
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("consent_given", true)
                .putBoolean("data_collection_enabled", false)
                .apply()
            goToMain()
        }
    }

    // only reached once consent has been given (via btnAgree), so this never runs without consent
    private fun detectRegionAndSave() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            saveConsentAndRegion("GLOBAL")
            return
        }

        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location ->
                val region = if (location != null) {
                    when {
                        location.latitude in 8.0..37.0 && location.longitude in 68.0..97.0 -> "IN"
                        location.latitude in 47.0..55.0 && location.longitude in 6.0..15.0 -> "DE"
                        else -> "GLOBAL"
                    }
                } else "GLOBAL"
                saveConsentAndRegion(region)
            }
            .addOnFailureListener {
                saveConsentAndRegion("GLOBAL")
            }
    }

    private fun saveConsentAndRegion(region: String) {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("region", region)
            .apply()

        lifecycleScope.launch {
            userPreferencesDao.insert(
                UserPreferences(
                    consentGiven = true,
                    detectedRegion = region,
                    useDarkMode = false
                )
            )
            goToMain()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
