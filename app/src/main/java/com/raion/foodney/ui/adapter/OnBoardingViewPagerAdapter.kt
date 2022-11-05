package com.raion.foodney.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
): FragmentStateAdapter(fm, lifecycle) {

    private val listOfFragments = ArrayList<Fragment>()

    fun setAllFragments(fragment: List<Fragment>) {
        listOfFragments.apply {
            clear()
            addAll(fragment)
        }
    }

    override fun getItemCount() = listOfFragments.size

    override fun createFragment(position: Int): Fragment = listOfFragments[position]
}