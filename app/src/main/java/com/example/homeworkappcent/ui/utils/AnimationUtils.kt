package com.example.homeworkappcent.ui.utils

import android.view.View

private const val DURATION = 250L

fun View.animateShown() {
    if (this.visibility == View.VISIBLE) {
        return
    }

    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate().alpha(1f).setDuration(DURATION).start()
}

fun View.animateHidden() {
    if (this.visibility == View.GONE) {
        return
    }

    this.animate().alpha(0f).setDuration(DURATION).start()
    this.postDelayed({
        this.visibility = View.GONE
    }, DURATION)
}