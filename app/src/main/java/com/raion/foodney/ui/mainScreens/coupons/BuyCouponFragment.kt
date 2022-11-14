package com.raion.foodney.ui.mainScreens.coupons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentBuyCouponBinding


class BuyCouponFragment : Fragment() {

    private lateinit var binding: FragmentBuyCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBuyCouponBinding.inflate(inflater, container, false)





        return binding.root
    }
}