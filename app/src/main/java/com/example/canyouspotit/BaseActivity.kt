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

// Every screen extends this instead of AppCompatActivity directly, so the light sensor
// listener spans onResume/onPause of whichever screen is currently in the foreground
// rather than being tied to one specific Activity that may finish immediately (MainActivity).
abstract class BaseActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // currentlyDark defaults to false at process start, which doesn't necessarily match
        // AppCompatDelegate's actual persisted mode (it survives process death). Sync it once,
        // from the resolved Configuration - not AppCompatDelegate.getDefaultNightMode(), which
        // can report MODE_NIGHT_UNSPECIFIED/FOLLOW_SYSTEM instead of an actual yes/no - so the
        // light-mode transition below isn't permanently unreachable after a dark mode carries
        // over from a previous session.
        if (!hasSyncedInitialDarkState) {
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            currentlyDark = nightModeFlags == Configuration.UI_MODE_NIGHT_YES
            hasSyncedInitialDarkState = true
        }
        // Sensor must always win over the phone's system dark/light setting. Reassert our own
        // explicit mode on every single Activity creation, not just reactively from the sensor -
        // otherwise a freshly created Activity can transiently pick up the system's DayNight
        // resource resolution before the sensor gets a chance to override it again.
        AppCompatDelegate.setDefaultNightMode(
            if (currentlyDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    // Whether the user allowed their scan/practice history to be persisted (chosen once on
    // the consent screen). Gates every Room write in the app. Absent key -> false: fail
    // closed on privacy, so a user who somehow reaches a screen without having answered -
    // or an install predating this flag - is treated as having declined until they choose.
    protected fun isDataCollectionEnabled(): Boolean =
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("data_collection_enabled", false)

    // Calm, palette-tinted Snackbar for passive acknowledgements (difficulty changes,
    // "data deleted", etc.) - kombu_green surface + centred bone text, LENGTH_LONG, no
    // action. Both colours have values-night overrides, so it stays legible in either mode.
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

    // Hysteresis (30/70 dead zone) + debounce (~1.75s sustained) so brief hand-waves near the
    // sensor don't flip the theme - only a real, sustained light-level change does. State is
    // in the companion object since it must survive screen transitions (onPause/onResume),
    // not reset per-Activity.
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_LIGHT) return
        val lux = event.values[0]
        val now = SystemClock.elapsedRealtime()

        // Cold-launch bypass: apply the very first real reading immediately, no debounce, so a
        // dark room shows dark mode from the first frame instead of a light flash that corrects
        // a moment later. A dead-zone first reading falls back to the Configuration-derived
        // currentlyDark from onCreate rather than waiting. Every later reading goes through the
        // normal hysteresis + debounce below.
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
