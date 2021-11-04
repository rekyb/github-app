package com.rekyb.jyro.ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentFavouritesBinding
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.ui.adapter.FavouritesListAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.navigateTo
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites),
    FavouritesListAdapter.Listener {

    private val viewModel: FavouritesViewModel by viewModels()

    private var listAdapter: FavouritesListAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setFavouritesDataObserver()
    }

    override fun onItemClick(view: View, data: UserDetailsModel) {
        data.userName?.let { username ->
            view.navigateTo(FavouritesFragmentDirections.passResult(username))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        listAdapter = null
        recyclerView = null
        recyclerView?.adapter = null
    }

    private fun setAdapter() {
        listAdapter = FavouritesListAdapter(this)

        recyclerView = binding?.rvFavList
        recyclerView?.adapter = listAdapter
    }

    private fun setFavouritesDataObserver() {
        viewModel.favouritesState.observe(viewLifecycleOwner, {
            when (val result = viewModel.favouritesState.value) {
                is DataState.Loading -> onLoad()
                is DataState.Success -> onSuccess(result.data)
                is DataState.Error -> onError(result.message)
            }
        })
    }

    private fun onLoad() {
        binding?.apply {
            progressBar.show()
            tvPlaceholder.hide()
        }
    }

    private fun onSuccess(result: List<UserDetailsModel>) {
        binding?.apply {
            listAdapter?.renderList(result)
            progressBar.hide()
            tvPlaceholder.hide()
            rvFavList.show()
        }
    }

    private fun onError(errorMessage: String) {
        binding?.apply {
            progressBar.hide()
            tvPlaceholder.apply {
                text = errorMessage
            }.show()
        }
    }
}
