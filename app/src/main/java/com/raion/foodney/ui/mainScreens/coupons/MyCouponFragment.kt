package com.raion.foodney.ui.mainScreens.coupons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentMyCouponBinding
import com.raion.foodney.ui.adapter.MyCouponAdapter
import com.raion.foodney.ui.mainScreens.MainViewModel

class MyCouponFragment : Fragment() {

    private lateinit var binding: FragmentMyCouponBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyCouponBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvMyCoupon.adapter = MyCouponAdapter()

        return binding.root
    }
}