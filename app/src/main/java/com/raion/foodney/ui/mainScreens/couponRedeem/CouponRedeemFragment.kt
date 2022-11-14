package com.raion.foodney.ui.mainScreens.couponRedeem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentCouponRedeemBinding

class CouponRedeemFragment : Fragment() {

    private lateinit var binding: FragmentCouponRedeemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCouponRedeemBinding.inflate(inflater, container, false)



        return binding.root
    }
}