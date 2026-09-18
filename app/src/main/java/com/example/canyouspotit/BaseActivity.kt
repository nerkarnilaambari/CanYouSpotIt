package com.example.canyouspotit

import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

// Base for every screen with UI. Hosts the light-sensor listener that switches the app
// between day and night themes.
abstract class BaseActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Static default is false; on a cold start, catch up to the real config once.
        if (!hasSyncedInitialDarkState) {
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            currentlyDark = nightModeFlags == Configuration.UI_MODE_NIGHT_YES
            hasSyncedInitialDarkState = true
        }
        // Set before super.onCreate() - that's when AppCompat reads the mode. Re-run on
        // every onCreate.
        AppCompatDelegate.setDefaultNightMode(
            if (currentlyDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    // Whether the user allowed scan/practice history to be saved (chosen on the consent
    // screen). Gates every Room write. Missing key reads as false.
    protected fun isDataCollectionEnabled(): Boolean =
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("data_collection_enabled", false)

    // Palette-tinted Snackbar for passive acknowledgements (difficulty changes, "data
    // deleted", etc.): kombu_green surface, centred bone text, LENGTH_LONG, no action.
    // Both colours have values-night overrides.
    protected fun showThemedSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).apply {
            setBackgroundTint(ContextCompat.getColor(this@BaseActivity, R.color.kombu_green))
            setTextColor(ContextCompat.getColor(this@BaseActivity, R.color.bone))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.apply {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                gravity = Gravity.CENTER
            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // Hysteresis (30/70 dead zone) + debounce (~1.75s sustained): the theme flips only on a
    // sustained light change. State lives in the companion object.
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_LIGHT) return
        val lux = event.values[0]
        val now = SystemClock.elapsedRealtime()

        // The first real reading applies immediately, skipping the debounce. A dead-zone
        // first reading keeps currentlyDark from onCreate. Later readings use the
        // hysteresis + debounce below.
        if (!hasAppliedInitialReading) {
            hasAppliedInitialReading = true
            currentlyDark = when {
                lux < DARK_THRESHOLD -> true
                lux > LIGHT_THRESHOLD -> false
                else -> currentlyDark
            }
            pendingDarkSince = null
            pendingLightSince = null
            AppCompatDelegate.setDefaultNightMode(
                if (currentlyDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            return
        }

        when {
            lux < DARK_THRESHOLD -> {
                pendingLightSince = null
                if (!currentlyDark) {
                    val since = pendingDarkSince ?: now.also { pendingDarkSince = it }
                    if (now - since >= DEBOUNCE_MS) {
                        currentlyDark = true
                        pendingDarkSince = null
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
            lux > LIGHT_THRESHOLD -> {
                pendingDarkSince = null
                if (currentlyDark) {
                    val since = pendingLightSince ?: now.also { pendingLightSince = it }
                    if (now - since >= DEBOUNCE_MS) {
                        currentlyDark = false
                        pendingLightSince = null
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
            else -> {
                // Dead zone (30-70 lux): cancel any pending switch, don't change current mode.
                pendingDarkSince = null
                pendingLightSince = null
            }
        }
    }

    companion object {
        private const val DARK_THRESHOLD = 30f
        private const val LIGHT_THRESHOLD = 70f
        private const val DEBOUNCE_MS = 1750L

        private var currentlyDark = false
        private var hasSyncedInitialDarkState = false
        private var hasAppliedInitialReading = false
        private var pendingDarkSince: Long? = null
        private var pendingLightSince: Long? = null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
