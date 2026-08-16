package com.example.canyouspotit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.TextView

class LearnActivity : AppCompatActivity() {

    private lateinit var adapter: ScamCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCards)
        val resultOverlay = findViewById<FrameLayout>(R.id.resultOverlay)
        val resultCard = findViewById<CardView>(R.id.resultCard)
        val tvOverlayVerdict = findViewById<TextView>(R.id.tvOverlayVerdict)
        val tvOverlayExplanation = findViewById<TextView>(R.id.tvOverlayExplanation)

        val deck = placeholderExamples.toMutableList()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ScamCardAdapter(deck) { example, userSaidLegit ->
            val correct = userSaidLegit != example.isScam

            if (correct) {
                resultCard.setCardBackgroundColor(getColor(R.color.verdict_safe))
                tvOverlayVerdict.text = if (userSaidLegit) "Correct — Legitimate!" else "Correct — Scam!"
            } else {
                resultCard.setCardBackgroundColor(getColor(R.color.verdict_scam))
                tvOverlayVerdict.text = "Not quite"
            }
            resultCard.invalidateOutline()
            tvOverlayExplanation.text = example.explanation

            resultOverlay.visibility = android.view.View.VISIBLE
            resultCard.invalidateOutline()
            adapter.removeTopCard()
        }
        recyclerView.adapter = adapter

        resultOverlay.setOnClickListener {
            resultOverlay.visibility = android.view.View.GONE
        }
    }
}
