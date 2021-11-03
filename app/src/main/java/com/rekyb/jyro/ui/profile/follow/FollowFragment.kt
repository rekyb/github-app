package com.rekyb.jyro.ui.profile.follow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.R
import com.rekyb.jyro.common.Constants.FRAGMENT_FOLLOW_TYPE
import com.rekyb.jyro.common.Constants.FRAGMENT_USERNAME
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentFollowBinding
import com.rekyb.jyro.domain.model.UserItemsModel
import com.rekyb.jyro.ui.adapter.MainListAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.ui.profile.ProfileFragmentDirections
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.navigateTo
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowFragment : BaseFragment<FragmentFollowBinding>(R.layout.fragment_follow),
    MainListAdapter.Listener {

    private var username: String? = null
    private var followType: String? = null
    private var listAdapter: MainListAdapter? = null
    private var recyclerView: RecyclerView? = null

    private val viewModel: FollowViewModel by viewModels()

    companion object {
        fun newFollowFragmentInstance(userName: String, type: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(FRAGMENT_USERNAME, userName)
                    putString(FRAGMENT_FOLLOW_TYPE, type)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            username = getString(FRAGMENT_USERNAME)
            followType = getString(FRAGMENT_FOLLOW_TYPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        getFollowData()
        setFollowDataObserver()
    }

    override fun onItemClick(view: View, data: UserItemsModel) {
        view.navigateTo(
            ProfileFragmentDirections.actionNavigateToSelf(data.userName)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        listAdapter = null
        recyclerView = null
    }

    private fun setAdapter() {
        listAdapter = MainListAdapter(this)

        recyclerView = binding?.rvFollowList
        recyclerView?.adapter = listAdapter
    }

    private fun getFollowData() {
        when (followType) {
            requireContext().getString(R.string.label_following) -> username?.let {
                viewModel.getFollowingList(it)
            }
            requireContext().getString(R.string.label_followers) -> username?.let {
                viewModel.getFollowersList(it)
            }
        }
    }

    private fun setFollowDataObserver() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // Observe following state
                launch {
                    viewModel.followingState
                        .collect { state ->
                            when (val followingResult = state.result) {
                                is DataState.Loading -> onLoading()
                                is DataState.Success -> onSuccess(
                                    isFollower = false,
                                    isEmptyResult = followingResult.data.isEmpty(),
                                    items = followingResult.data
                                )
                                is DataState.Error -> onError(followingResult.message)
                            }
                        }
                }

                // Observe followers state
                launch {
                    viewModel.followersState
                        .collect { state ->
                            when (val followerResult = state.result) {
                                is DataState.Loading -> onLoading()
                                is DataState.Success -> onSuccess(
                                    isFollower = true,
                                    isEmptyResult = followerResult.data.isEmpty(),
                                    items = followerResult.data
                                )
                                is DataState.Error -> onError(followerResult.message)
                            }
                        }
                }

            }
        }
    }

    private fun onLoading() {
        binding?.apply {
            rvFollowList.hide()
            tvPlaceholder.hide()
        }
    }

    private fun onSuccess(
        isFollower: Boolean,
        isEmptyResult: Boolean,
        items: List<UserItemsModel>,
    ) {

        binding?.apply {
            when {
                isEmptyResult && isFollower -> {
                    onError("$username " +
                            requireContext().getString(R.string.error_empty_follower))
                }
                isEmptyResult && !isFollower -> {
                    onError("$username " +
                            requireContext().getString(R.string.error_empty_following))
                }
                else -> {
                    listAdapter?.renderList(items)

                    rvFollowList.show()
                    tvPlaceholder.hide()
                }
            }
        }

    }

    private fun onError(errorMessage: String) {
        binding?.apply {
            rvFollowList.hide()
            tvPlaceholder.apply {
                text = errorMessage
            }.show()
        }
    }
}