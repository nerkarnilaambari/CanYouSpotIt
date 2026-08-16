package com.example.canyouspotit

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.abs

class SwipeableCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var onSwipedRight: (() -> Unit)? = null
    var onSwipedLeft: (() -> Unit)? = null

    private var startX = 0f
    private var startY = 0f
    private var dX = 0f
    private var dY = 0f
    private val swipeThreshold = 300f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                parent?.requestDisallowInterceptTouchEvent(true)
                startX = event.rawX
                startY = event.rawY
                dX = translationX - event.rawX
                dY = translationY - event.rawY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val newTranslationX = event.rawX + dX
                val newTranslationY = event.rawY + dY
                translationX = newTranslationX
                translationY = newTranslationY
                rotation = (newTranslationX - startX) / 20f
                return true
            }
            MotionEvent.ACTION_UP -> {
                val distanceX = event.rawX - startX
                if (abs(distanceX) > swipeThreshold) {
                    if (distanceX > 0) {
                        animate().translationXBy(800f).rotation(30f).setDuration(250)
                            .withEndAction { onSwipedRight?.invoke() }.start()
                    } else {
                        animate().translationXBy(-800f).rotation(-30f).setDuration(250)
                            .withEndAction { onSwipedLeft?.invoke() }.start()
                    }
                } else {
                    animate().translationX(0f).translationY(0f).rotation(0f).setDuration(200).start()
                }
                parent?.requestDisallowInterceptTouchEvent(false)
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                animate().translationX(0f).translationY(0f).rotation(0f).setDuration(200).start()
                parent?.requestDisallowInterceptTouchEvent(false)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun reset() {
        translationX = 0f
        translationY = 0f
        rotation = 0f
    }
}
