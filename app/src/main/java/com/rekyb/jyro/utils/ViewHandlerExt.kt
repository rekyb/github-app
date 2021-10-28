package com.rekyb.jyro.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
    return this
}

fun View.navigateTo(direction: NavDirections) {
    Navigation.findNavController(this).navigate(direction)
}