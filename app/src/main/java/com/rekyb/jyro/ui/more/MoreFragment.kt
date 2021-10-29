package com.rekyb.jyro.ui.more

import android.os.Bundle
import android.view.View
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.FragmentMoreBinding
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvPlaceholder?.text = activity?.application?.getString(R.string.label_more)
    }
}
