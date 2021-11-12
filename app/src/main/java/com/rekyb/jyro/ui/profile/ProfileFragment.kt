package com.rekyb.jyro.ui.profile

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rekyb.jyro.R
import com.rekyb.jyro.common.Resources
import com.rekyb.jyro.databinding.FragmentProfileBinding
import com.rekyb.jyro.domain.model.UserDetailsModel
import com.rekyb.jyro.ui.adapter.ViewPagerAdapter
import com.rekyb.jyro.ui.base.BaseFragment
import com.rekyb.jyro.utils.hide
import com.rekyb.jyro.utils.show
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val args by navArgs<ProfileFragmentArgs>()
    private val viewModel: ProfileViewModel by viewModels()

    private var fbFavourite: FloatingActionButton? = null
    private var fbAdd: FloatingActionButton? = null
    private var fbShare: FloatingActionButton? = null
    private var tabs: TabLayout? = null
    private var viewPager: ViewPager2? = null
    private var isItFavourite = false
    private var isClicked = false

    private val rotateOpenAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open,
        )
    }

    private val rotateCloseAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close,
        )
    }

    private val fromBottomAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom,
        )
    }

    private val toBottomAnim: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetails(args.username)

        setComponents()
        setProfileDataState()
    }

    private fun setComponents() {
        val tabsTitles = listOf(
            requireContext().getString(R.string.label_following),
            requireContext().getString(R.string.label_followers)
        )

        binding?.apply {
            fbFavourite = fabFavorite
            fbAdd = fabAdd
            fbShare = fabShare
            tabs = tabLayout
            viewPager = profileViewPager
        }

        fbAdd?.setOnClickListener { onAddButtonClicked() }
        fbFavourite?.setOnClickListener { setFavourite() }
        fbShare?.setOnClickListener {
            FancyToast.makeText(
                requireContext(),
                "Share",
                FancyToast.LENGTH_SHORT,
                FancyToast.INFO,
                false
            ).show()
        }

        viewPager?.adapter = ViewPagerAdapter(this, args.username, tabsTitles)

        TabLayoutMediator(tabs!!, viewPager!!) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun toggleFabIcon(state: Boolean) {
        viewModel.checkIsUserOnFavList(args.username)

        if (state) {
            fbFavourite!!.setImageResource(R.drawable.ic_fav_filled)
        } else {
            fbFavourite!!.setImageResource(R.drawable.ic_fav_border)
        }
    }

    private fun setFavourite() {
        viewModel.apply {
            if (!isItFavourite) {
                addUserToFavList(binding?.userdata!!)
                FancyToast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.notify_added_to_list),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,
                    false
                ).show()
                toggleFabIcon(false)
            } else {
                removeUserFromFavList(binding?.userdata!!)
                FancyToast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.notify_removed_from_list),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.INFO,
                    false
                ).show()
                toggleFabIcon(true)
            }
        }
    }

    private fun setProfileDataState() {
        viewModel.profileState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when (state.result) {
                    is Resources.Loading -> onLoad()
                    is Resources.Success -> {
                        onSuccess(state.result)

                        isItFavourite = state.isUserListedAsFavourite ?: false
                        toggleFabIcon(isItFavourite)
                    }
                    is Resources.Error -> onError(state.result.message)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onAddButtonClicked() {
        setFabVisibility(isClicked)
        setFabAnimation(isClicked)
        isClicked = !isClicked
    }

    private fun setFabVisibility(state: Boolean) {
        if (!state) {
            fbShare?.show()
            fbFavourite?.show()
        } else {
            fbShare?.hide()
            fbFavourite?.hide()
        }
    }

    private fun setFabAnimation(state: Boolean) {
        if (!state) {
            fbAdd?.startAnimation(rotateOpenAnim)
            fbFavourite?.startAnimation(fromBottomAnim)
            fbShare?.startAnimation(fromBottomAnim)
        } else {
            fbAdd?.startAnimation(rotateCloseAnim)
            fbFavourite?.startAnimation(toBottomAnim)
            fbShare?.startAnimation(toBottomAnim)
        }
    }

    private fun onLoad() {
        binding?.apply {
            progressBar.show()
            profileContentWrapper.hide()
            tvPlaceholder.hide()
            fbAdd?.hide()
        }
    }

    private fun onSuccess(state: Resources.Success<UserDetailsModel>) {
        binding?.apply {
            userdata = state.data
            progressBar.hide()
            profileContentWrapper.show()
            tvPlaceholder.hide()
            fbAdd?.show()
        }
    }

    private fun onError(errorMessage: String) {
        binding?.apply {
            progressBar.hide()
            profileContentWrapper.hide()
            tvPlaceholder.apply {
                text = errorMessage
            }.show()
            fbAdd?.hide()
        }
    }
}
