package com.raion.foodney.ui.mainScreens.couponRedeem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentCouponRedeemBinding
import com.raion.foodney.ui.mainScreens.MainViewModel

class CouponRedeemFragment : Fragment() {

    private lateinit var binding: FragmentCouponRedeemBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCouponRedeemBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        binding.btnAdd.setOnClickListener {
            findNavController().navigate(CouponRedeemFragmentDirections.actionCouponRedeemFragmentToWarungListFragment())
        }

        return binding.root
    }
}