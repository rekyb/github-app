package com.rekyb.jyro.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun View.show(): View {
    if (visibility != VISIBLE) {
        visibility = VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != INVISIBLE) {
        visibility = INVISIBLE
    }
    return this
}

fun TextView.setTopDrawable(drawable: Drawable?) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
}

fun View.navigateTo(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}