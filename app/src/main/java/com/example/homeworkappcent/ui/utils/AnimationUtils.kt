package com.example.homeworkappcent.ui.utils

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentContainerView

object AnimationUtils {

    fun showUI(fragmentContainerView: FragmentContainerView, progress: LinearLayout) {
        fragmentContainerView.animateShown()
        progress.animateHidden()
    }

    fun hideUI(gameListLayout: LinearLayout, progress: LinearLayout) {
        gameListLayout.animateHidden()
        progress.animateShown()
    }
}

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