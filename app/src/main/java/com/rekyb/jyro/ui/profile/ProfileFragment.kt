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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val args by navArgs<ProfileFragmentArgs>()
    private val viewModel: ProfileViewModel by viewModels()

    private var viewPager: ViewPager2? = null
    private var tabs: TabLayout? = null
    private var userData: UserDetailsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(args.username)

        setComponents()
        setProfileDataCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        tabs = null
        viewPager = null
    }

    private fun setComponents() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewPager = profileViewPager
            tabs = tabLayout
            fabFavorite.setOnClickListener {
                setFavourite()
            }
        }

        val tabsTitles = listOf(
            requireContext().getString(R.string.label_following),
            requireContext().getString(R.string.label_followers)
        )

        viewPager?.adapter = ViewPagerAdapter(this, args.username, tabsTitles)

        TabLayoutMediator(tabs!!, viewPager!!) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun setFavourite() {
        viewModel.apply {
            val state = profileState.value.isUserListedAsFavourite

            state?.let { value ->
                if (!value) {
                    FancyToast.makeText(requireContext(),
                        "Added to your list",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false).show()
                    addUserToFavList(userData!!)
                } else {
                    FancyToast.makeText(requireContext(),
                        "Removed from your list",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.INFO,
                        false).show()
                    removeUserFromFavList(userData!!)
                }

                toggleFabIcon(value)
            }
        }
    }

    private fun toggleFabIcon(state: Boolean) {
        viewModel.checkIsUserOnFavList(userData?.id!!)

        binding?.apply {
            if (state) {
                fabFavorite.setImageResource(R.drawable.ic_fav_filled)
            } else {
                fabFavorite.setImageResource(R.drawable.ic_fav_border)
            }
        }
    }

    private fun setProfileDataCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileState
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collect { state ->
                    binding?.run {
                        when (state.result) {
                            is DataState.Loading -> onLoading()
                            is DataState.Success -> {
                                onSuccess(state.result)
                                userData = state.result.data

                                state.isUserListedAsFavourite?.let { toggleFabIcon(it) }
                            }
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
        fabFavorite.hide()
    }

    private fun FragmentProfileBinding.onSuccess(state: DataState.Success<UserDetailsModel>) {
        userdata = state.data
        progressBar.hide()
        profileContentWrapper.show()
        tvPlaceholder.hide()
        fabFavorite.show()
    }

    private fun FragmentProfileBinding.onError(errorMessage: String) {
        progressBar.hide()
        profileContentWrapper.hide()
        tvPlaceholder.apply {
            text = errorMessage
        }.show()
        fabFavorite.hide()
    }
}
