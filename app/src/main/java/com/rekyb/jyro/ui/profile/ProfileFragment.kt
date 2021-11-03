package com.rekyb.jyro.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rekyb.jyro.R
import com.rekyb.jyro.common.DataState
import com.rekyb.jyro.databinding.FragmentProfileBinding
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.ui.adapter.ViewPagerAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.show
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val args by navArgs<ProfileFragmentArgs>()
    private val viewModel: ProfileViewModel by viewModels()

    private var viewPager: ViewPager2? = null
    private var tabs: TabLayout? = null
    private var isItFavourite: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(args.username)

        setComponents()
        setProfileDataObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        tabs = null
        viewPager = null
    }

    private fun setComponents() {
        val tabsTitles = listOf(
            requireContext().getString(R.string.label_following),
            requireContext().getString(R.string.label_followers)
        )

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewPager = profileViewPager
            tabs = tabLayout
            fabFavorite.setOnClickListener {
                setFavourite(isItFavourite)
            }
        }

        viewPager?.adapter = ViewPagerAdapter(this, args.username, tabsTitles)

        TabLayoutMediator(tabs!!, viewPager!!) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun toggleFabIcon(state: Boolean) {
        viewModel.checkIsUserOnFavList(binding?.userdata?.id!!)

        binding?.apply {
            if (state) {
                fabFavorite.setImageResource(R.drawable.ic_fav_filled)
            } else {
                fabFavorite.setImageResource(R.drawable.ic_fav_border)
            }
        }
    }

    private fun setFavourite(state: Boolean?) {
        viewModel.apply {

            state?.let { value ->
                if (!value) {
                    FancyToast.makeText(requireContext(),
                        "Added to your list",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false).show()
                    addUserToFavList(binding?.userdata!!)
                } else {
                    FancyToast.makeText(requireContext(),
                        "Removed from your list",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.INFO,
                        false).show()
                    removeUserFromFavList(binding?.userdata!!)
                }

                toggleFabIcon(value)
            }
        }
    }

    private fun setProfileDataObserver() {
        viewModel.profileState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .flowOn(Dispatchers.Main)
            .onEach { state ->
                when (state.result) {
                    is DataState.Loading -> onLoading()
                    is DataState.Success -> {
                        onSuccess(state.result)

                        isItFavourite = state.isUserListedAsFavourite
                        isItFavourite?.let { toggleFabIcon(it) }
                    }
                    is DataState.Error -> onError(state.result.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onLoading() {
        binding?.apply {
            progressBar.show()
            profileContentWrapper.hide()
            tvPlaceholder.hide()
            fabFavorite.hide()
        }
    }

    private fun onSuccess(state: DataState.Success<UserDetailsModel>) {
        binding?.apply {
            userdata = state.data

            progressBar.hide()
            profileContentWrapper.show()
            tvPlaceholder.hide()
            fabFavorite.show()
        }
    }

    private fun onError(errorMessage: String) {
        binding?.apply {
            progressBar.hide()
            profileContentWrapper.hide()
            tvPlaceholder.apply {
                text = errorMessage
            }.show()
            fabFavorite.hide()
        }
    }
}
