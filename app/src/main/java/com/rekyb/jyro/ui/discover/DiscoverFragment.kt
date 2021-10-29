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
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentDiscoverBinding
import com.rekyb.jyro.domain.model.UserItems
import com.rekyb.jyro.ui.adapter.AdapterDataObserver
import com.rekyb.jyro.ui.adapter.DiscoverUserAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.setTopDrawable
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment :
    BaseFragment<FragmentDiscoverBinding>(R.layout.fragment_discover),
    SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    private val userAdapter by lazy { DiscoverUserAdapter() }
    private val viewModel: DiscoverViewModel by navGraphViewModels(R.id.app_navigation) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setDataCollector()
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        recyclerView.layoutManager?.onSaveInstanceState().also { viewModel.scrollState = it }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar_search, menu)

        val searchMenuItem = menu.findItem(R.id.app_bar_search)

        searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            queryHint = requireContext().getString(R.string.query_hint)
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

    private fun setAdapter() {
        recyclerView = binding?.rvSearchResults!!
        recyclerView.adapter = userAdapter

        if (viewModel.scrollState != null) {
            userAdapter.registerAdapterDataObserver(
                AdapterDataObserver(
                    recyclerView,
                    viewModel.scrollState!!
                )
            )
        }
    }

    private fun setDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    binding?.apply {
                        when (val result = state.result) {
                            is DataState.Success -> {
                                result.data.apply {
                                    onSuccess(
                                        isEmptyResults = totalCount == 0,
                                        items = userItems
                                    )
                                }
                            }
                            is DataState.Error -> onError(result.message)
                            is DataState.Loading -> onLoading()
                        }
                    }
                }
        }
    }

    private fun FragmentDiscoverBinding.onSuccess(isEmptyResults: Boolean, items: List<UserItems>) {
        progressBar.hide()

        if (isEmptyResults) {
            onError(requireContext().getString(R.string.error_not_found))
        } else {
            userAdapter.renderList(items)
            rvSearchResults.show()
            tvPlaceholder.hide()
        }
    }

    private fun FragmentDiscoverBinding.onError(errorMessage: String) {
        rvSearchResults.hide()
        tvPlaceholder.apply {
            text = errorMessage
            setTopDrawable(
                AppCompatResources
                    .getDrawable(requireContext(), R.drawable.ic_exclamation_mark)
            )
        }.show()
    }

    private fun FragmentDiscoverBinding.onLoading() {
        progressBar.show()
        tvPlaceholder.hide()
        rvSearchResults.hide()
    }
}
