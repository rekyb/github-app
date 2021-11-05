package com.rekyb.jyro.ui.more

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import coil.imageLoader
import coil.util.CoilUtils
import com.rekyb.jyro.R
import com.rekyb.jyro.common.Themes
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

        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (preference is ListPreference) {
                val index = preference.findIndexOfValue(newValue.toString())
                val selection = preference.entries[index]

                onSelectionThemeChanged(selection.toString())
            }

            true
        }

        val cleanPreference =
            findPreference<Preference>(requireContext().getString(R.string.key_clear_cache))
        val defaultTheme = Themes.SYSTEM_DEFAULT.selection

        cleanPreference?.setOnPreferenceClickListener {
            onWipeCacheClicked(requireContext())

            themePreference?.value = defaultTheme
            onSelectionThemeChanged(defaultTheme)

            FancyToast.makeText(requireContext(),
                getString(R.string.notify_cache_cleaned),
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                false).show()

            true
        }
    }

    private fun onSelectionThemeChanged(selection: String) {
        val themeChanger = ThemeChanger()

        themeChanger.changeBy(selection)
        viewModel.saveSelectedAppTheme(selection)
    }

    private fun onWipeCacheClicked(context: Context) {
        val imageLoader = context.imageLoader
        val cache = CoilUtils.createDefaultCache(context)

        try {
            imageLoader.memoryCache.clear()
            cache.directory.deleteRecursively()
            viewModel.clearAppPref()
        } catch (e: Exception) {
            FancyToast.makeText(requireContext(),
                e.message,
                FancyToast.LENGTH_SHORT,
                FancyToast.ERROR,
                false).show()
        }
    }
}

