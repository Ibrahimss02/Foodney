package com.raion.foodney.ui.mainScreens.coupons

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentBuyCouponBinding
import com.raion.foodney.models.Coupon
import com.raion.foodney.ui.adapter.CouponAdapter
import com.raion.foodney.ui.adapter.CouponOnClickListener
import com.raion.foodney.ui.mainScreens.MainViewModel


class BuyCouponFragment : Fragment() {

    private lateinit var binding: FragmentBuyCouponBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBuyCouponBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.rvBuyCoupon.adapter = CouponAdapter(CouponOnClickListener {
            showDialog(it)
        })

        return binding.root
    }

    private fun showDialog(coupon: Coupon) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Beli Kupon ${coupon.name}? ")
        builder.setMessage("Poin kamu akan dikurangi senilai ${coupon.cost}")
            .setPositiveButton(
                "Tukar"
            ) { dialog, id ->
                viewModel.buyCoupon(coupon)
            }
            .setNegativeButton(
                "Batal"
            ) { dialog, id ->
                // User cancelled the dialog
            }
        // Create the AlertDialog object and return it
        builder.create().show()
    }
}