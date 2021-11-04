package com.rekyb.jyro.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rekyb.jyro.ui.follow.FollowFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val username: String,
    private val tabs: List<String>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newFollowFragmentInstance(username, tabs[position])
    }
}
