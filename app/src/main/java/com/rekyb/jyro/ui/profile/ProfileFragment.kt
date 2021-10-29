package com.rekyb.jyro.ui.profile

import android.os.Bundle
import android.view.View
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.FragmentProfileBinding
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvPlaceholder?.text = getString(R.string.label_profile)
    }
}
