package com.example.canyouspotit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.TextView
import com.example.canyouspotit.data.AppDatabase
import com.example.canyouspotit.data.LearningSession
import com.example.canyouspotit.model.ScamExample
import com.example.canyouspotit.model.scamExamples
import kotlinx.coroutines.launch

class LearnActivity : BaseActivity() {

    private lateinit var adapter: ScamCardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var resultOverlay: FrameLayout
    private lateinit var resultCard: CardView
    private lateinit var tvOverlayVerdict: TextView
    private lateinit var tvOverlayCategory: TextView
    private lateinit var tvOverlayExplanation: TextView
    private lateinit var tvDifficultyChip: TextView

    private val learningSessionDao by lazy { AppDatabase.getDatabase(this).learningSessionDao() }

    private var detectedRegion = "GLOBAL"
    private var currentDifficulty = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        recyclerView = findViewById(R.id.recyclerViewCards)
        resultOverlay = findViewById(R.id.resultOverlay)
        resultCard = findViewById(R.id.resultCard)
        tvOverlayVerdict = findViewById(R.id.tvOverlayVerdict)
        tvOverlayCategory = findViewById(R.id.tvOverlayCategory)
        tvOverlayExplanation = findViewById(R.id.tvOverlayExplanation)
        tvDifficultyChip = findViewById(R.id.tvDifficultyChip)

        // Room's UserPreferences.detectedRegion is write-only (nothing ever reads it back);
        // app_prefs/"region" is the actual value MainActivity's consent gate relies on, so
        // that's the source of truth here too. Missing key (e.g. consent declined) falls
        // back to GLOBAL, matching ConsentActivity's own GPS-failure fallback.
        detectedRegion = getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getString("region", "GLOBAL") ?: "GLOBAL"

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadDeckForLevel(currentDifficulty)

        resultOverlay.setOnClickListener {
            resultOverlay.visibility = android.view.View.GONE
        }

        // Tapping a chip is equivalent to swiping the top card that direction - it routes
        // through the identical onAnswer path. Swipe gesture handling is unchanged.
        // Ignored while the result overlay is up (mid-feedback).
        findViewById<TextView>(R.id.chipScam).setOnClickListener {
            if (resultOverlay.visibility != android.view.View.VISIBLE) {
                adapter.answerTopCard(userSaidLegit = false)
            }
        }
        findViewById<TextView>(R.id.chipLegit).setOnClickListener {
            if (resultOverlay.visibility != android.view.View.VISIBLE) {
                adapter.answerTopCard(userSaidLegit = true)
            }
        }

        // Intercept the system back gesture/button via the modern dispatcher so we can show
        // the all-time category recap before finishing. Callback is always enabled; the
        // recap itself is conditionally skipped when there's nothing to celebrate yet.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showRecapThenFinish()
            }
        })
    }

    // On leaving the Learn screen, reward learning breadth: pull every correct attempt ever
    // recorded, resolve each back to its scam category, and show the distinct set. No scores,
    // no counts, no pass/fail - consistent with the app's "never quiz language" principle.
    private fun showRecapThenFinish() {
        lifecycleScope.launch {
            val categoryLabels = learningSessionDao.getAllCorrectSessions()
                // Correctly-identified legitimate messages have no scamCategory - filter them
                // out here; only scam categories count toward this recap.
                .mapNotNull { session ->
                    scamExamples.firstOrNull { it.id == session.exampleId }?.scamCategory
                }
                .map { categoryLabel(it) }
                .distinct()

            if (categoryLabels.isEmpty()) {
                // Never correctly spotted a scam yet - skip the recap entirely, no awkward
                // empty dialog, just navigate back normally.
                finish()
                return@launch
            }

            showCategoryRecap(categoryLabels)
        }
    }

    private fun showCategoryRecap(categoryLabels: List<String>) {
        val content = layoutInflater.inflate(R.layout.dialog_category_recap, null)
        content.findViewById<TextView>(R.id.tvRecapCategories).text =
            categoryLabels.joinToString("\n") { "•  $it" }

        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(content)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // "Got it" - and any other dismissal - completes the back navigation.
        content.findViewById<Button>(R.id.btnRecapGotIt).setOnClickListener { dialog.dismiss() }
        dialog.setOnDismissListener { finish() }
        dialog.show()
    }

    // Difficulty-advancement acknowledgement - a palette-tinted Snackbar instead of a plain
    // Toast (which renders in system styling, outside the app's design). Shared styling lives
    // in BaseActivity.showThemedSnackbar so this and the settings screen stay consistent.
    private fun showDifficultySnackbar(message: String) = showThemedSnackbar(message)

    private fun buildDeckForLevel(level: Int): MutableList<ScamExample> {
        val tierExamples = scamExamples
            .filter { (it.region == detectedRegion || it.region == "GLOBAL") && it.difficulty == level }
            .shuffled()
        // A region+difficulty combo could theoretically be empty (e.g. no Hard-tier IN
        // examples). Rather than show a blank deck, fall back to the region's full pool
        // shuffled, so the practice flow never dead-ends.
        return (if (tierExamples.isNotEmpty()) tierExamples
        else scamExamples.filter { it.region == detectedRegion || it.region == "GLOBAL" }.shuffled())
            .toMutableList()
    }

    private fun loadDeckForLevel(level: Int) {
        val deck = buildDeckForLevel(level)
        adapter = ScamCardAdapter(deck) { example, userSaidLegit, decisionTimeMs ->
            handleAnswer(example, userSaidLegit, decisionTimeMs)
        }
        recyclerView.adapter = adapter
        updateDifficultyChip()
    }

    private fun updateDifficultyChip() {
        tvDifficultyChip.text = when (currentDifficulty) {
            2 -> "Medium"
            3 -> "Hard"
            else -> "Easy"
        }
    }

    private fun handleAnswer(example: ScamExample, userSaidLegit: Boolean, decisionTimeMs: Long) {
        val correct = userSaidLegit != example.isScam

        if (correct) {
            resultCard.setCardBackgroundColor(getColor(R.color.verdict_safe))
            tvOverlayVerdict.text = if (userSaidLegit) "Correct — Legitimate!" else "Correct — Scam!"
        } else {
            resultCard.setCardBackgroundColor(getColor(R.color.verdict_scam))
            tvOverlayVerdict.text = "Not quite"
        }
        resultCard.invalidateOutline()

        val category = example.scamCategory
        if (example.isScam && category != null) {
            tvOverlayCategory.text = categoryLabel(category)
            tvOverlayCategory.visibility = android.view.View.VISIBLE
        } else {
            tvOverlayCategory.visibility = android.view.View.GONE
        }

        // "Fast" = answered in under FAST_ANSWER_THRESHOLD_MS. Adjust here if the
        // coaching nudge should trigger at a different speed.
        tvOverlayExplanation.text = if (!correct && decisionTimeMs < FAST_ANSWER_THRESHOLD_MS) {
            "${example.explanation} This one's easy to miss when moving quickly. Worth a second look next time."
        } else {
            example.explanation
        }

        resultOverlay.visibility = android.view.View.VISIBLE
        resultCard.invalidateOutline()
        adapter.removeTopCard()

        val levelAtAnswerTime = currentDifficulty
        lifecycleScope.launch {
            // The correct/incorrect overlay above always shows; only the history write is
            // gated. A declining user's session counts stay at 0, so difficulty simply
            // never advances - the intended, graceful consequence of not saving history.
            if (isDataCollectionEnabled()) {
                learningSessionDao.insert(
                    LearningSession(
                        exampleId = example.id,
                        userAnswer = if (userSaidLegit) "Legitimate" else "Scam",
                        isCorrect = correct,
                        difficultyLevel = levelAtAnswerTime,
                        timestamp = System.currentTimeMillis(),
                        decisionTimeMs = decisionTimeMs
                    )
                )
            }

            // Only evaluate advancement once at least 5 cards have been answered at this
            // level, so one lucky/unlucky early streak can't flip the tier prematurely.
            val total = learningSessionDao.getTotalCountForLevel(levelAtAnswerTime)
            if (total >= 5) {
                val correctCount = learningSessionDao.getCorrectCountForLevel(levelAtAnswerTime)
                val accuracy = correctCount.toDouble() / total
                if (accuracy >= 0.8 && currentDifficulty < 3) {
                    currentDifficulty++
                    val levelName = if (currentDifficulty == 2) "Medium" else "Hard"
                    showDifficultySnackbar("Moving to $levelName difficulty")
                    loadDeckForLevel(currentDifficulty)
                    return@launch
                }
            }

            // Didn't advance this turn. If the current tier's shuffled pool has run out
            // before the accuracy threshold was met, cycle back through the same tier's
            // examples again (reshuffled) rather than leaving the deck empty.
            if (adapter.itemCount == 0) {
                loadDeckForLevel(currentDifficulty)
            }
        }
    }

    private fun categoryLabel(category: String): String = when (category) {
        "nuisance_marketing_spam" -> "Marketing spam"
        "account_verification_phishing" -> "Account verification phishing"
        "prize_advance_fee_scam" -> "Prize / advance-fee scam"
        "police_impersonation_scam" -> "Police impersonation scam"
        "tech_support_scam" -> "Tech support scam"
        "police_blackmail_scam" -> "Police blackmail scam"
        "bank_kyc_otp_scam" -> "Bank/KYC scam"
        "fake_emergency_scam" -> "Fake emergency scam"
        "romance_or_stranded_traveler_scam" -> "Romance / stranded traveler scam"
        "credential_harvesting_scam" -> "Credential harvesting scam"
        "government_debt_threat_scam" -> "Government debt threat scam"
        "generic_phishing" -> "Phishing"
        "delivery_scam" -> "Delivery scam"
        "premium_rate_call_scam" -> "Premium-rate call scam"
        "loan_advance_fee_scam" -> "Loan advance-fee scam"
        "unclear_scam_type" -> "Scam"
        else -> category.replace('_', ' ').replaceFirstChar { it.uppercase() }
    }

    companion object {
        // Threshold for the fast-wrong coaching nudge. Change this single value to
        // adjust what counts as "moving quickly".
        private const val FAST_ANSWER_THRESHOLD_MS = 2000L
    }
}
