package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Launcher: routes to consent or home, then finishes.
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)

        if (!prefs.getBoolean("consent_given", false)) {
            startActivity(Intent(this, ConsentActivity::class.java))
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        finish()
    }
}
