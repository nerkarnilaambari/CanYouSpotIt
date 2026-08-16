package com.example.canyouspotit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMessage = findViewById<EditText>(R.id.etMessage)
        val etLink = findViewById<EditText>(R.id.etLink)
        val btnScan = findViewById<Button>(R.id.btnScan)

        btnScan.setOnClickListener {
            // Classifier logic will be wired in here once PhishingClassifier.kt is built
        }
    }
}
