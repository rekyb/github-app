package com.rekyb.jyro.ui.more

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.rekyb.jyro.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_main, rootKey)

        listPreferenceListener()
    }

    private fun listPreferenceListener() {
        val themePreference =
            findPreference<ListPreference>(requireContext().getString(R.string.key_list_theme))

        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val entry = preference.entries[index]

                themeChanger(entry)
            }

            true
        }

    }

    private fun themeChanger(selection: CharSequence) {
        when (selection) {
            "Use device theme" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            "Light theme" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark theme" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}

