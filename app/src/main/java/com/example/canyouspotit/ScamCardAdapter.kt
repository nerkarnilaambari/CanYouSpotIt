package com.example.canyouspotit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScamCardAdapter(
    private val examples: MutableList<ScamExample>,
    private val onAnswer: (ScamExample, Boolean) -> Unit
) : RecyclerView.Adapter<ScamCardAdapter.CardViewHolder>() {

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

        val example = examples[position]
        holder.tvMessage.text = example.messageText

        holder.swipeableCard.onSwipedRight = {
            onAnswer(example, true)
        }
        holder.swipeableCard.onSwipedLeft = {
            onAnswer(example, false)
        }
    }

    override fun getItemCount(): Int = examples.size

    fun removeTopCard() {
        if (examples.isNotEmpty()) {
            examples.removeAt(0)
            notifyItemRangeRemoved(0, 1)
        }
    }
}
