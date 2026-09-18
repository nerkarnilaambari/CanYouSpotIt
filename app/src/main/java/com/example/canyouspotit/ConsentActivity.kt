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
        if (granted) detectRegionAndSave() else saveConsentAndRegion("GLOBAL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent)

        findViewById<Button>(R.id.btnAgree).setOnClickListener {
            // consent_given = the consent question was answered (MainActivity's startup gate);
            // data_collection_enabled = the answer was yes, save history.
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
            // Consent question answered - the answer was just "no". No Room write;
            // data_collection_enabled = false gates all writes.
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
