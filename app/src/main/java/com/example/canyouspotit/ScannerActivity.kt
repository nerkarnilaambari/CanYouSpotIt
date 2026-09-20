package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

// Activity 1: paste a message, tap Scan, PhishingClassifier gives the verdict card,
// Continue carries it to the emotional-response screen.
class ScannerActivity : BaseActivity() {

    private val scanResultDao by lazy { AppDatabase.getDatabase(this).scanResultDao() }

    // Set once the Room insert for the current scan completes. The Continue button reads
    // these at click time.
    private var lastScanId: Int = 0
    private var lastVerdict: String = "SAFE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyNavigationBarBottomInset(findViewById(R.id.contentColumn))

        val etMessage = findViewById<EditText>(R.id.etMessage)
        val etLink = findViewById<EditText>(R.id.etLink)
        val cardLink = findViewById<CardView>(R.id.cardLink)
        val btnScan = findViewById<Button>(R.id.btnScan)
        val cardResult = findViewById<CardView>(R.id.cardResult)
        val tvVerdict = findViewById<TextView>(R.id.tvVerdict)
        val tvExplanation = findViewById<TextView>(R.id.tvExplanation)
        val tvActionStep = findViewById<TextView>(R.id.tvActionStep)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        // Once a result shows, the inputs collapse: the Message box shrinks to a 2-line
        // preview and the empty Link card hides. Editing either field expands them again.
        val defaultMessageMinLines = etMessage.minLines
        val defaultMessageMaxLines = etMessage.maxLines

        fun collapseInputsForResult() {
            etMessage.minLines = 1
            etMessage.maxLines = 2
            etMessage.setSelection(0) // show the start of the message in the collapsed preview
            if (etLink.text.isNullOrBlank()) cardLink.visibility = View.GONE
        }

        fun expandInputs() {
            etMessage.minLines = defaultMessageMinLines
            etMessage.maxLines = defaultMessageMaxLines
            cardLink.visibility = View.VISIBLE
        }

        val restoreOnEdit = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (cardResult.visibility == View.VISIBLE) expandInputs()
            }
        }
        etMessage.addTextChangedListener(restoreOnEdit)
        etLink.addTextChangedListener(restoreOnEdit)

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
            // Action-step text colour, one per verdict.
            tvActionStep.setTextColor(
                when (result.verdict) {
                    "SCAM" -> getColor(R.color.verdict_scam_action)
                    "CAUTION" -> getColor(R.color.verdict_caution_action)
                    else -> getColor(R.color.verdict_safe_action)
                }
            )
            cardResult.visibility = View.VISIBLE
            btnContinue.visibility = View.VISIBLE
            collapseInputsForResult()

            lastVerdict = result.verdict

            // The dominant manipulation tactic among this scan's flags. Always null for SAFE.
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

            // The verdict card always shows; only the save is gated on consent. Without a
            // save, lastScanId stays 0 and downstream screens read that as "no saved scan".
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
