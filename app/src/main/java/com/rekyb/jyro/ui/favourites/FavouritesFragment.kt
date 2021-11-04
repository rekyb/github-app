package com.rekyb.jyro.ui.favourites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isEmpty
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
import com.rekyb.jyro.utils.setTopDrawable
import com.rekyb.jyro.utils.show
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment :
    BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites),
    FavouritesListAdapter.Listener {

    private val viewModel: FavouritesViewModel by viewModels()

    private var listAdapter: FavouritesListAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setFavouritesDataObserver()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar_clear, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.app_bar_clear_list) {
            clearList()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
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

    private fun clearList() {
        if (recyclerView!!.isEmpty()) {
            FancyToast.makeText(
                requireContext(),
                requireContext().getString(R.string.notify_list_empty),
                FancyToast.LENGTH_SHORT,
                FancyToast.CONFUSING,
                false
            ).show()
        } else {
            FancyToast.makeText(
                requireContext(),
                requireContext().getString(R.string.notify_list_delated),
                FancyToast.LENGTH_SHORT,
                FancyToast.SUCCESS,
                false
            ).show()

            viewModel.clearList()
        }
    }

    private fun defaultState() {
        binding?.apply {
            tvPlaceholder.apply {
                text = requireContext().getString(R.string.empty_fav_list)
                setTopDrawable(
                    AppCompatResources
                        .getDrawable(requireContext(), R.drawable.ic_default_state)
                )
            }.show()
            rvFavList.hide()
        }
    }

    private fun onLoad() {
        binding?.apply {
            tvPlaceholder.hide()
            rvFavList.hide()
        }
    }

    private fun onSuccess(result: List<UserDetailsModel>) {
        binding?.apply {
            if (result.isEmpty()) {
                defaultState()
            } else {
                listAdapter?.renderList(result)

                tvPlaceholder.hide()
                rvFavList.show()
            }
        }
    }

    private fun onError(errorMessage: String) {
        binding?.apply {
            rvFavList.hide()
            tvPlaceholder.apply {
                text = errorMessage
                setTopDrawable(
                    AppCompatResources
                        .getDrawable(requireContext(), R.drawable.ic_exclamation_mark)
                )
            }.show()
        }
    }
}
