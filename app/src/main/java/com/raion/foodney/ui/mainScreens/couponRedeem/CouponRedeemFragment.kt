package com.raion.foodney.ui.mainScreens.couponRedeem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.raion.foodney.databinding.FragmentCouponRedeemBinding
import com.raion.foodney.ui.mainScreens.coupons.Constants.TAB_TITLES
import com.raion.foodney.ui.mainScreens.coupons.CouponAdapter

class CouponRedeemFragment : Fragment() {

    private lateinit var binding: FragmentCouponRedeemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCouponRedeemBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = CouponAdapter(activity as AppCompatActivity)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()



        return binding.root
    }
}