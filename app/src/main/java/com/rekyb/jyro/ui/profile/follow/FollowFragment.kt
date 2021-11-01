package com.rekyb.jyro.ui.profile.follow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import es.dmoral.toasty.Toasty
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
        setFollowDataCollector()
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
            "Following" -> username?.let { viewModel.getFollowingList(it) }
            "Followers" -> username?.let { viewModel.getFollowersList(it) }
        }
    }

    private fun setFollowDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.followState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    binding?.apply {
                        when (val following = state.followingResult) {
                            is DataState.Loading -> onLoading()
                            is DataState.Success -> onSuccess(
                                isEmptyResult = following.data.isEmpty(),
                                isDataFollower = false,
                                items = following.data
                            )
                            is DataState.Error -> onError(following.message)
                        }
                    }

                    binding?.apply {
                        when (val followers = state.followersResult) {
                            is DataState.Loading -> onLoading()
                            is DataState.Success -> onSuccess(
                                isEmptyResult = followers.data.isEmpty(),
                                isDataFollower = true,
                                items = followers.data
                            )
                            is DataState.Error -> onError(followers.message)
                        }
                    }
                }

        }
    }

    private fun FragmentFollowBinding.onLoading() {
        with(requireContext()) {
            Toasty.info(
                this,
                getString(R.string.label_getting_data),
                Toast.LENGTH_SHORT
            ).show()
            rvFollowList.hide()
        }
    }

    private fun FragmentFollowBinding.onSuccess(
        isEmptyResult: Boolean,
        isDataFollower: Boolean,
        items: List<UserItemsModel>,
    ) {

        when {
            isEmptyResult && isDataFollower -> {
                onError(requireContext().getString(R.string.error_empty_follower))
            }
            isEmptyResult && !isDataFollower -> {
                onError(requireContext().getString(R.string.error_following_empty))
            }
            else -> {
                listAdapter?.renderList(items)
                rvFollowList.show()
            }
        }

    }

    private fun FragmentFollowBinding.onError(errorMessage: String) {
        Toasty.success(
            requireContext(),
            errorMessage,
            Toast.LENGTH_LONG
        ).show()
        rvFollowList.hide()
    }
}