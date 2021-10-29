package com.rekyb.jyro.ui.favourites

import android.os.Bundle
import android.view.View
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.FragmentFavouritesBinding
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvPlaceholder?.text = activity?.application?.getString(R.string.label_favourites)
    }
}
