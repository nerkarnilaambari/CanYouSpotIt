package com.example.canyouspotit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.UserPreferences
import kotlinx.coroutines.launch
import java.util.Locale

class ConsentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consent)

        val userPreferencesDao = AppDatabase.getDatabase(this).userPreferencesDao()

        findViewById<Button>(R.id.btnAgree).setOnClickListener {
            // this is the flag MainActivity's consent gate actually checks on startup
            getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("consent_given", true)
                .apply()

            lifecycleScope.launch {
                // detectedRegion is a placeholder until real location detection is wired in
                userPreferencesDao.insert(
                    UserPreferences(
                        consentGiven = true,
                        detectedRegion = Locale.getDefault().country,
                        useDarkMode = false
                    )
                )
                goToMain()
            }
        }

        findViewById<Button>(R.id.btnDecline).setOnClickListener {
            // user chose not to save data, so we skip writing to the database entirely
            goToMain()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
