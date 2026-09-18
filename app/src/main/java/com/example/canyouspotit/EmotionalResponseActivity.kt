package com.example.canyouspotit

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.canyouspotit.data.AppDatabase
import kotlinx.coroutines.launch

// Activity 3, from the Scanner's Continue button: asks how the message felt before the
// verdict was known, then shows a reflection for that (emotion, verdict) pair plus an
// optional note. All skippable, only saved when data collection is on.
class EmotionalResponseActivity : BaseActivity() {

    private val scanResultDao by lazy { AppDatabase.getDatabase(this).scanResultDao() }
    private var scanId: Int = -1   // -1 = no saved scan to attach to
    private var verdict: String = "SAFE"
    private var primaryTactic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotional_response)

        scanId = intent.getIntExtra("scanId", -1)
        verdict = intent.getStringExtra("verdict") ?: "SAFE"

        // Loads primaryTactic from the saved scan into the field up front.
        if (scanId != -1) {
            lifecycleScope.launch {
                primaryTactic = scanResultDao.getById(scanId)?.primaryTactic
            }
        }

        val tvVerdictBadge = findViewById<TextView>(R.id.tvVerdictBadge)
        tvVerdictBadge.text = verdict
        // Badge text colour, one per verdict and per theme (verdict_*_badge).
        tvVerdictBadge.setTextColor(
            when (verdict) {
                "SCAM" -> getColor(R.color.verdict_scam_badge)
                "CAUTION" -> getColor(R.color.verdict_caution_badge)
                else -> getColor(R.color.verdict_safe_badge)
            }
        )

        val choicesContainer = findViewById<View>(R.id.choicesContainer)
        val reflectionContainer = findViewById<View>(R.id.reflectionContainer)
        val tvReflection = findViewById<TextView>(R.id.tvReflection)
        val etNote = findViewById<EditText>(R.id.etNote)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)
        val btnDone = findViewById<Button>(R.id.btnDone)

        // Each label string is also the key reflectionFor() and saveEmotion() match on.
        val emotions = listOf(
            R.id.btnScared to "Scared or panicked",
            R.id.btnConfused to "Confused or unsure",
            R.id.btnSuspicious to "Suspicious already",
            R.id.btnNormal to "Nothing unusual — seemed normal"
        )

        emotions.forEach { (viewId, label) ->
            findViewById<Button>(viewId).setOnClickListener {
                choicesContainer.visibility = View.GONE
                val base = reflectionFor(verdict, label)
                val tacticSentence = primaryTactic?.let { tacticSentenceFor(it) }
                tvReflection.text = if (tacticSentence != null) "$base $tacticSentence" else base
                reflectionContainer.visibility = View.VISIBLE
                saveEmotion(label)
            }
        }

        btnDone.setOnClickListener {
            // Save the note before leaving; a blank field writes null.
            val noteText = etNote.text?.toString()
            lifecycleScope.launch {
                persistNote(noteText)
                goHome()
            }
        }
        tvSkip.setOnClickListener { goHome() }
    }

    private fun reflectionFor(verdict: String, label: String): String = when (verdict) {
        "SCAM" -> when (label) {
            "Scared or panicked" ->
                "Feeling scared is exactly what scammers want. They use fear deliberately to make you act without thinking. You were right to pause. Fear is the signal to slow down, not speed up."
            "Confused or unsure" ->
                "Confusion is a sign the message was designed to mislead you. Legitimate organisations communicate clearly and simply. When something feels confusing, that confusion itself is the warning sign."
            "Suspicious already" ->
                "Your instinct was right. That suspicion is a skill, and one you can sharpen. The more you practise recognising these patterns, the faster you will spot them automatically."
            else ->
                "This message was carefully designed to look completely normal. That is what makes it dangerous. The most successful scams are the ones that feel completely routine. This is why awareness matters."
        }
        "CAUTION" -> when (label) {
            "Scared or panicked" ->
                "It's understandable to feel scared here. This message showed some warning signs, so treating it carefully instead of acting right away was a smart move."
            "Confused or unsure" ->
                "Feeling unsure makes sense. This message had a few signs worth questioning, so pausing to think it through, like you did, is exactly the right response."
            "Suspicious already" ->
                "Good instinct. This message did show some genuine warning signs, so your caution here was well placed."
            else ->
                "This one actually had a few signs worth noticing, even though it seemed routine at first. It's a good reminder to look a little closer, even when something feels normal."
        }
        else -> when (label) {
            "Scared or panicked" ->
                "It's completely understandable to feel scared before knowing more. This message turned out to be genuinely safe, but that instinct to pause and check first is exactly the right one to keep."
            "Confused or unsure" ->
                "Feeling unsure is a good reason to double-check, even for real messages. This one turned out to be genuine, but checking first instead of guessing is always the safer habit."
            "Suspicious already" ->
                "This one turned out to be genuine, but there's nothing wrong with double-checking anything that feels unusual. Staying alert is a good habit, even when a message turns out safe."
            else ->
                "That matches what the scan found. Genuine messages usually do feel normal and routine, so trusting that feeling here was reasonable."
        }
    }

    private fun tacticSentenceFor(tactic: String): String = when (tactic) {
        "URGENCY" -> "It also created urgency, so you wouldn't stop to think."
        "REWARD" -> "It also promised something exciting, which is easy to want to believe."
        else -> "It also used fear, so you wouldn't stop to question it."
    }

    private fun saveEmotion(label: String) {
        val id = scanId
        if (id == -1) return
        // The reflection already shows; only the save is gated on consent.
        if (!isDataCollectionEnabled()) return
        lifecycleScope.launch {
            val scan = scanResultDao.getById(id)
            if (scan != null) {
                scanResultDao.update(scan.copy(emotionalResponse = label))
            }
        }
    }

    // Optional free-text note, written on "Done". A blank / whitespace-only field writes
    // null. Gated on consent.
    private suspend fun persistNote(rawNote: String?) {
        val id = scanId
        if (id == -1) return
        if (!isDataCollectionEnabled()) return
        val note = rawNote?.trim()?.takeIf { it.isNotEmpty() }
        scanResultDao.updateNote(id, note)
    }

    private fun goHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(homeIntent)
        finish()
    }
}
