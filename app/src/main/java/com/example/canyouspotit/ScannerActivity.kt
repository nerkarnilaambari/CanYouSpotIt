package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.classifier.PhishingClassifier
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.ScanResult
import kotlinx.coroutines.launch

class ScannerActivity : BaseActivity() {

    private val scanResultDao by lazy { AppDatabase.getDatabase(this).scanResultDao() }

    // Set once the Room insert for the current scan completes; the Continue button
    // reads these at click time rather than capturing them at listener-registration
    // time, so it always reflects whichever scan most recently finished saving.
    private var lastScanId: Int = 0
    private var lastVerdict: String = "SAFE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etMessage = findViewById<EditText>(R.id.etMessage)
        val etLink = findViewById<EditText>(R.id.etLink)
        val btnScan = findViewById<Button>(R.id.btnScan)
        val cardResult = findViewById<CardView>(R.id.cardResult)
        val tvVerdict = findViewById<TextView>(R.id.tvVerdict)
        val tvExplanation = findViewById<TextView>(R.id.tvExplanation)
        val tvActionStep = findViewById<TextView>(R.id.tvActionStep)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        btnScan.setOnClickListener {
            val combinedText = listOf(etMessage.text.toString(), etLink.text.toString())
                .filter { it.isNotBlank() }
                .joinToString(" ")

            val result = PhishingClassifier.classify(combinedText)

            tvVerdict.text = result.verdict
            tvExplanation.text = result.explanation
            tvActionStep.text = when (result.verdict) {
                "SCAM" -> "Do not click any links or share any details. Close this message and block the sender if you can."
                "CAUTION" -> "Don't click or reply yet. If you want to be sure, call them using a number you already know, not one from this message."
                else -> "No warning signs here, but it's always fine to double-check anything that asks for money or personal details."
            }

            cardResult.setCardBackgroundColor(
                when (result.verdict) {
                    "SCAM" -> getColor(R.color.verdict_scam)
                    "CAUTION" -> getColor(R.color.verdict_caution)
                    else -> getColor(R.color.verdict_safe)
                }
            )
            cardResult.visibility = View.VISIBLE
            btnContinue.visibility = View.VISIBLE

            lastVerdict = result.verdict

            // Which manipulation tactic dominated the flags this scan tripped. Forced to
            // null for SAFE verdicts explicitly (not just "flags happened to be empty"),
            // since a SAFE message can still trip an occasional flag before legitimate-
            // phrase deductions pull the score back down — we never want to suggest a
            // genuinely safe message used a manipulation tactic.
            val urgencyCount = result.flags.count { it.startsWith("Urgency detected") }
            val rewardCount = result.flags.count { it.startsWith("Reward lure") }
            val threatCount = result.flags.count { it.startsWith("Suspicious indicator") }
            val primaryTactic: String? = if (result.verdict == "SAFE") {
                null
            } else {
                val maxCount = maxOf(urgencyCount, rewardCount, threatCount)
                when {
                    maxCount == 0 -> null
                    urgencyCount == maxCount -> "URGENCY"
                    rewardCount == maxCount -> "REWARD"
                    else -> "THREAT"
                }
            }

            // Classification and the verdict card above always run; only persistence is
            // gated. A declining user keeps lastScanId = 0, which downstream screens treat
            // as "no saved scan" without error.
            if (isDataCollectionEnabled()) {
                lifecycleScope.launch {
                    val id = scanResultDao.insert(
                        ScanResult(
                            messageText = combinedText,
                            verdict = result.verdict,
                            timestamp = System.currentTimeMillis(),
                            primaryTactic = primaryTactic
                        )
                    )
                    lastScanId = id.toInt()
                }
            }
        }

        btnContinue.setOnClickListener {
            val intent = Intent(this, EmotionalResponseActivity::class.java)
            intent.putExtra("scanId", lastScanId)
            intent.putExtra("verdict", lastVerdict)
            startActivity(intent)
        }
    }
}
