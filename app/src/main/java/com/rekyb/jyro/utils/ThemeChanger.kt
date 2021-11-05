package com.rekyb.jyro.utils

import androidx.appcompat.app.AppCompatDelegate
import com.rekyb.jyro.common.Themes

class ThemeChanger {

    fun changeBy(selection: String) {

        when (selection) {
            Themes.SYSTEM_DEFAULT.selection -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            Themes.LIGHT_THEME.selection -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Themes.DARK_THEME.selection -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}
