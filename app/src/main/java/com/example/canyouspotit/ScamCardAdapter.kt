package com.example.canyouspotit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.canyouspotit.model.ScamExample

class ScamCardAdapter(
    private val examples: MutableList<ScamExample>,
    private val onAnswer: (ScamExample, Boolean, Long) -> Unit
) : RecyclerView.Adapter<ScamCardAdapter.CardViewHolder>() {

    // When the current top card was shown - the answer handlers diff against it for decision time.
    private var cardShownAtMs: Long = 0

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvCardMessage)
        val swipeableCard: SwipeableCardView = view.findViewById(R.id.swipeableCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_scam_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.swipeableCard.reset()
        cardShownAtMs = System.currentTimeMillis()

        val example = examples[position]
        holder.tvMessage.text = example.messageText

        holder.swipeableCard.onSwipedRight = {
            onAnswer(example, true, System.currentTimeMillis() - cardShownAtMs)
        }
        holder.swipeableCard.onSwipedLeft = {
            onAnswer(example, false, System.currentTimeMillis() - cardShownAtMs)
        }
    }

    override fun getItemCount(): Int = examples.size

    fun removeTopCard() {
        if (examples.isNotEmpty()) {
            examples.removeAt(0)
            notifyItemRangeRemoved(0, 1)
        }
    }

    // Chip taps ("Scam" / "Legitimate") answer the top card through the same onAnswer
    // callback and timing basis as a swipe.
    fun answerTopCard(userSaidLegit: Boolean) {
        if (examples.isEmpty()) return
        onAnswer(examples[0], userSaidLegit, System.currentTimeMillis() - cardShownAtMs)
    }
}
