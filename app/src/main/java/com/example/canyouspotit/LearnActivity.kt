package com.example.canyouspotit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.LinearLayout
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

        // UserPreferences.detectedRegion is write-only; app_prefs/"region" is the value read
        // at runtime. Missing key falls back to GLOBAL.
        detectedRegion = getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getString("region", "GLOBAL") ?: "GLOBAL"

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadDeckForLevel(currentDifficulty)

        resultOverlay.setOnClickListener {
            resultOverlay.visibility = android.view.View.GONE
        }

        // Chip taps route through the same onAnswer path as a swipe of the top card.
        // Ignored while the result overlay is up.
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

        // Intercepts the system back gesture: shows the category recap, then finishes.
        // The recap skips itself when there's nothing to show yet.
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showRecapThenFinish()
            }
        })
    }

    // On the way out, shows how much the user has practised each scam category. Bar length
    // is attempt count (correct or not). Legit examples have no scamCategory and are excluded.
    private fun showRecapThenFinish() {
        lifecycleScope.launch {
            val categoryCounts: List<Pair<String, Int>> = learningSessionDao.getAll()
                .mapNotNull { session ->
                    scamExamples.firstOrNull { it.id == session.exampleId }?.scamCategory
                }
                .groupingBy { categoryLabel(it) }
                .eachCount()
                .toList()
                .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })

            if (categoryCounts.isEmpty()) {
                // Nothing practised yet - skip the recap and just go back.
                finish()
                return@launch
            }

            showCategoryRecap(categoryCounts)
        }
    }

    private fun showCategoryRecap(categoryCounts: List<Pair<String, Int>>) {
        val content = layoutInflater.inflate(R.layout.dialog_category_recap, null)
        val chart = content.findViewById<LinearLayout>(R.id.recapChartContainer)

        // Scale each bar against the most-practised category. coerceAtLeast(1) guards the
        // all-zero case.
        val maxCount = (categoryCounts.maxOfOrNull { it.second } ?: 1).coerceAtLeast(1)
        val density = resources.displayMetrics.density
        fun dp(value: Int) = (value * density).toInt()

        categoryCounts.forEachIndexed { index, (label, count) ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { if (index > 0) topMargin = dp(14) }
            }

            val labelView = TextView(this).apply {
                text = label
                setTextAppearance(R.style.TextAppearance_CanYouSpotIt_Body)
                setTextColor(getColor(R.color.forest_shade))
            }

            val barRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, dp(18)
                ).apply { topMargin = dp(6) }
            }
            val bar = View(this).apply {
                setBackgroundResource(R.drawable.bg_recap_bar)
                minimumWidth = dp(10) // a practised category always shows at least this much
                layoutParams = LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, count.toFloat()
                )
            }
            val countView = TextView(this).apply {
                text = count.toString()
                setTextAppearance(R.style.TextAppearance_CanYouSpotIt_Body)
                setTextColor(getColor(R.color.forest_shade))
                textSize = 13f
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { marginStart = dp(8) }
            }
            val spacer = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(0, 1, (maxCount - count).toFloat())
            }
            barRow.addView(bar)
            barRow.addView(countView)
            barRow.addView(spacer)

            row.addView(labelView)
            row.addView(barRow)
            chart.addView(row)
        }

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

        // "Got it", or any other dismissal, completes the back navigation.
        content.findViewById<Button>(R.id.btnRecapGotIt).setOnClickListener { dialog.dismiss() }
        dialog.setOnDismissListener { finish() }
        dialog.show()
    }

    // Delegates to BaseActivity.showThemedSnackbar.
    private fun showDifficultySnackbar(message: String) = showThemedSnackbar(message)

    // app_prefs, not Room - this must persist regardless of whether the user has practice
    // history saving on, same as consent_given and region.
    private fun hasShownHardTierCompletionMessage(): Boolean =
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .getBoolean("hard_tier_completion_shown", false)

    private fun markHardTierCompletionMessageShown() {
        getSharedPreferences("app_prefs", MODE_PRIVATE)
            .edit()
            .putBoolean("hard_tier_completion_shown", true)
            .apply()
    }

    private fun buildDeckForLevel(level: Int): MutableList<ScamExample> {
        val tierExamples = scamExamples
            .filter { (it.region == detectedRegion || it.region == "GLOBAL") && it.difficulty == level }
            .shuffled()
        // A region+difficulty combo can come up empty (e.g. no Hard-tier IN examples); fall
        // back to the region's full pool shuffled.
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

        // Extra nudge when a wrong answer also came in fast.
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
            // The overlay always shows; only the history write is gated on consent. With no
            // history, level counts stay at 0 and difficulty holds at the starting tier.
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

            // Only check advancement once there are 5+ answers at this level.
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
                } else if (accuracy >= 0.8 && currentDifficulty == 3 && !hasShownHardTierCompletionMessage()) {
                    markHardTierCompletionMessageShown()
                    showDifficultySnackbar(HARD_TIER_COMPLETION_MESSAGE)
                }
            }

            // Didn't advance. If this tier's shuffled pool ran out, reload it reshuffled.
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
        // What counts as "moving quickly" for the fast-wrong nudge.
        private const val FAST_ANSWER_THRESHOLD_MS = 2000L

        // Shown once, ever, on reaching 80%+ accuracy at Hard - the top tier, nothing to
        // advance to.
        private const val HARD_TIER_COMPLETION_MESSAGE =
            "You've practiced spotting scams across every difficulty level. That kind of " +
                "awareness is real, lasting protection."
    }
}
