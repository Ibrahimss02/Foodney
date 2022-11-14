package com.raion.foodney.ui.mainScreens.coupons

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.raion.foodney.ui.mainScreens.coupons.Constants.TAB_TITLES

class CouponAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = BuyCouponFragment()
            1 -> fragment = MyCouponFragment()
        }
        return fragment as Fragment
    }
}