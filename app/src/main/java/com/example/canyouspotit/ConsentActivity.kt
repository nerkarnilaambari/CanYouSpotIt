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

    // Must be registered before onStart.
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) detectRegionAndSave() else saveConsentAndRegion("GLOBAL", regionResolved = true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent)

        findViewById<Button>(R.id.btnAgree).setOnClickListener {
            // consent_given tracks that the question was answered; data_collection_enabled tracks whether the answer was yes.
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
            // Declined: data_collection_enabled=false gates all Room writes; no row inserted here.
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("consent_given", true)
                .putBoolean("data_collection_enabled", false)
                .apply()
            goToMain()
        }
    }

    // Only reached after consent is given via btnAgree.
    private fun detectRegionAndSave() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            saveConsentAndRegion("GLOBAL", regionResolved = true)
            return
        }

        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    saveConsentAndRegion(classifyRegion(location.latitude, location.longitude), regionResolved = true)
                } else {
                    // No cached fix yet - HomeActivity retries with an active request later.
                    saveConsentAndRegion("GLOBAL", regionResolved = false)
                }
            }
            .addOnFailureListener {
                saveConsentAndRegion("GLOBAL", regionResolved = false)
            }
    }

    private fun saveConsentAndRegion(region: String, regionResolved: Boolean) {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("region", region)
            .putBoolean("region_resolved", regionResolved)
            .apply()

        lifecycleScope.launch {
            userPreferencesDao.insert(
                UserPreferences(
                    consentGiven = true,
                    detectedRegion = region
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

// Shared with HomeActivity's silent retry.
fun classifyRegion(latitude: Double, longitude: Double): String = when {
    latitude in 8.0..37.0 && longitude in 68.0..97.0 -> "IN"
    latitude in 47.0..55.0 && longitude in 6.0..15.0 -> "DE"
    else -> "GLOBAL"
}
