package com.rekyb.jyro.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import com.rekyb.jyro.R
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover),
    SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar_search, menu)

        val searchMenuItem = menu.findItem(R.id.app_bar_search)

        searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            queryHint = activity?.getString(R.string.query_hint)
            maxWidth = Integer.MAX_VALUE

            setOnQueryTextListener(this@DiscoverFragment)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}