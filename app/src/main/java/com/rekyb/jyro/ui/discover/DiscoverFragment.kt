package com.rekyb.jyro.ui.discover

import android.os.Bundle
import android.view.View
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment: BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvPlaceholder?.text = activity?.application?.getString(R.string.label_discover)
    }

}