package com.rekyb.jyro.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentProfileBinding
import com.rekyb.jyro.domain.model.GetDetailsModel
import com.rekyb.jyro.ui.MainActivity
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val args by navArgs<ProfileFragmentArgs>()
    private val viewModel: ProfileVIewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(args.username)
    }

    override fun onResume() {
        super.onResume()
        setProfileDataCollector()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).hideBottomNavView()
        super.onAttach(context)
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavView()
        super.onDetach()
    }

    private fun setProfileDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                   binding?.run {
                       when(state.result) {
                           is DataState.Loading -> onLoading()
                           is DataState.Success -> onSuccess(state.result)
                           is DataState.Error -> onError(state.result.message)
                       }
                   }
                }
        }
    }

    private fun FragmentProfileBinding.onLoading() {
        progressBar.show()
        profileContentWrapper.hide()
        tvPlaceholder.hide()
    }

    private fun FragmentProfileBinding.onSuccess(state: DataState.Success<GetDetailsModel>) {
        userdata = state.data
        progressBar.hide()
        profileContentWrapper.show()
        tvPlaceholder.hide()
    }

    private fun FragmentProfileBinding.onError(errorMessage: String) {
        progressBar.hide()
        profileContentWrapper.hide()
        tvPlaceholder.apply {
            text = errorMessage
        }.show()
    }
}
