package tw.edu.pu.myapp.library

import android.view.View
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import tw.edu.pu.myapp.R

/**
 * how to use it
 * binding.root.setOnClickListener{
 *      binding.recordTitle.slideRightOut(1000L, 0L)
 *      Handler(Looper.getMainLooper()).postDelayed({binding.recordTitle.visibility = View.GONE}, 1000L)
 * }
 */
fun View.slideRightOut(animTime: Long, startOffset: Long) {
    val slideRight = AnimationUtils.loadAnimation(context, R.anim.slide_right_out).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }

    startAnimation(slideRight)
}

fun View.fadeIn(animTime: Long, startOffset: Long) {
    val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }

    startAnimation(fadeIn)
}

fun View.fadeOut(animTime: Long, startOffset: Long) {
    val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }

    startAnimation(fadeOut)
}