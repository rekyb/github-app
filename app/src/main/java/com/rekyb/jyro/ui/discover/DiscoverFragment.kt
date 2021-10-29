package com.rekyb.jyro.ui.discover

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.ui.adapter.DiscoverUserAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.setTopDrawable
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover),
    SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private val adapter by lazy { DiscoverUserAdapter() }
    private val viewModel: DiscoverViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDataCollector()
        setHasOptionsMenu(true)

        binding?.rvSearchResults?.adapter = adapter
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
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    private fun setupDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    binding?.apply {
                        when (state.result) {
                            is DataState.Loading -> onLoading()
                            is DataState.Error -> onError(state.result.message)
                            is DataState.Success -> {
                                val data = state.result.data

                                onSuccess(data.totalCount == 0)
                                adapter.renderList(data.userItems)
                            }
                        }
                    }
                }
        }
    }

    private fun FragmentDiscoverBinding.onLoading() {
        tvPlaceholder.hide()
        rvSearchResults.hide()
        progressBar.show()
    }

    private fun FragmentDiscoverBinding.onSuccess(isEmptyResults: Boolean) {
        if (isEmptyResults) {
            onError(requireContext().getString(R.string.error_not_found))
        } else {
            tvPlaceholder.hide()
            rvSearchResults.show()
            progressBar.hide()
        }
    }

    private fun FragmentDiscoverBinding.onError(errorMessage: String) {
        tvPlaceholder.apply {
            text = errorMessage
            setTopDrawable(
                AppCompatResources
                    .getDrawable(requireContext(), R.drawable.ic_exclamation_mark)
            )
        }.show()
        rvSearchResults.hide()
        progressBar.hide()
    }
}
