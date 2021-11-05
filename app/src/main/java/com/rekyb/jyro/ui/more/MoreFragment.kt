package com.rekyb.jyro.ui.more

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.rekyb.jyro.R
import com.rekyb.jyro.utils.ThemeChanger
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : PreferenceFragmentCompat() {

    private val viewModel: MoreViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_main, rootKey)

        preferenceListener()
    }

    private fun preferenceListener() {
        val themePreference =
            findPreference<ListPreference>(requireContext().getString(R.string.key_list_theme))

        val cleanPreference =
            findPreference<Preference>(requireContext().getString(R.string.key_clear_cache))

        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val selection = preference.entries[index]

                onSelectionThemeChanged(selection)
            }

            true
        }

        cleanPreference?.setOnPreferenceClickListener {

            viewModel.clearAppPref()

            FancyToast.makeText(requireContext(),
                "App cache has been cleaned!",
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                false).show()

            true
        }
    }

    private fun onSelectionThemeChanged(selection: CharSequence) {
        val themeChanger = ThemeChanger()

        themeChanger.changeBy(selection)
        viewModel.saveSelectedAppTheme(selection.toString())
    }
}

