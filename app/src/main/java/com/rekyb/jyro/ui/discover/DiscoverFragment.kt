package com.rekyb.jyro.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DiscoverFragment : BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover),
    SearchView.OnQueryTextListener {

    private val viewModel: DiscoverViewModel
            by navGraphViewModels(R.id.app_navigation) { defaultViewModelProviderFactory }
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDataCollector()
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
            viewModel.searchUser(query)
            searchView.clearFocus()

            setViewOnLoading(viewModel.dataState.value.isDataOnLoad)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    private fun setupDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state.result) {
                        is DataState.Success -> {
                            setViewOnResult()
                        }
                        is DataState.Error -> {
                            setViewOnError()
                            Timber.d(state.result.message)
                        }
                    }
                }
        }
    }

    private fun setViewOnLoading(state: Boolean) {
        if (state) {
            binding?.apply {
                tvPlaceholder.hide()
                rvSearchResults.hide()
                progressBar.show()
            }
        }
    }

    private fun setViewOnResult() {
        binding?.apply {
            tvPlaceholder.hide()
            rvSearchResults.show()
            progressBar.hide()
        }
    }

    private fun setViewOnError() {
        binding?.apply {
            tvPlaceholder.show()
            rvSearchResults.hide()
            progressBar.hide()
        }
    }
}