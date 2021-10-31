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
import com.rekyb.jyro.ui.MainActivity
import com.rekyb.jyro.ui.base.BaseFragment
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
                    when(state.result) {
                        is DataState.Success -> {
                            binding?.userdata = state.result.data
                        }
                        else -> binding?.userdata = null
                    }
                }
        }
    }
}
