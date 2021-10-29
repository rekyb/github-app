package com.rekyb.jyro.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.rekyb.jyro.R

fun View.show(): View {
    if (visibility != VISIBLE) {
        visibility = VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != GONE) {
        visibility = GONE
    }
    return this
}

fun TextView.setTopDrawable(drawable: Drawable?) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
}

fun View.navigateTo(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    val circleProgressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    this.load(url) {
        diskCachePolicy(CachePolicy.ENABLED)
        placeholder(circleProgressDrawable)
        error(R.drawable.ic_error_loading_image)
        crossfade(true)
    }
}
