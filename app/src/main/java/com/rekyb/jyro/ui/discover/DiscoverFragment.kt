package com.rekyb.jyro.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navGraphViewModels
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.domain.model.SearchResponse
import com.rekyb.jyro.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover),
    SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private val viewModel: DiscoverViewModel
            by navGraphViewModels(R.id.app_navigation) { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCollector()
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
        query?.let {

            lifecycleScope.launch {
                viewModel.searchUser(query)
                Timber.d("Launched search from viewModel")
            }

            searchView.clearFocus()
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    private fun setupCollector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataState.collect { state ->

                    setLoadingState(state.isDataOnLoad)

                    if (!state.isStandby){
                        ifErrorThenGet(state.result)
                        ifSuccessThenGet(state.result)
                    }
                }
            }
        }
    }

    private fun setLoadingState(state: Boolean) {
        if (state) {
            Toast.makeText(activity, "Data on load", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ifSuccessThenGet(state: DataState<SearchResponse>?) {
        if (state is DataState.Success) {
            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
            Timber.d("${state.data.userItems}")
        }
    }

    private fun ifErrorThenGet(state: DataState<SearchResponse>?) {
        if (state is DataState.Error) {
            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            Timber.d(state.message)
        }
    }
}