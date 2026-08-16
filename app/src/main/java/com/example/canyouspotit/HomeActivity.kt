package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<android.view.View>(R.id.btnGoToScanner).setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnGoToLearn).setOnClickListener {
            startActivity(Intent(this, LearnActivity::class.java))
        }
    }
}
