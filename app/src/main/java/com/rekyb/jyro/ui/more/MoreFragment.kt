package com.rekyb.jyro.ui.more

import android.os.Bundle

import androidx.preference.PreferenceFragmentCompat
import com.rekyb.jyro.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_screen_main, rootKey)
    }
}
