package com.raion.foodney.ui.welcomeScreens.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentOnBoardingBinding
import com.raion.foodney.ui.adapter.OnBoardingViewPagerAdapter

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var pageItems: ArrayList<Pair<Int, String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)

        pageItems = arrayListOf(
            Pair(R.drawable.iv_onboard_1, getString(R.string.first_onboard)),
            Pair(R.drawable.iv_onboard_2, getString(R.string.second_onboard)),
            Pair(R.drawable.iv_onboard_3, getString(R.string.third_onboard))
        )

        binding.viewPager.adapter = OnBoardingViewPagerAdapter(pageItems)
        binding.indicator.attachTo(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                onPageChanged(position)
            }
        })

        binding.fabNextPage.setOnClickListener {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }

        return binding.root
    }

    private fun onPageChanged(position: Int) {
        when (position) {
            (pageItems.size - 1) ->{
                binding.startButton.animate().apply {
                    duration = 500
                    alphaBy(1.0F)
                }.withStartAction {
                    binding.fabNextPage.visibility = View.INVISIBLE
                    binding.startButton.visibility = View.VISIBLE
                }.start()
            }
            else -> {
                binding.startButton.animate().apply {
                    duration = 500
                    alpha(0.0F)
                }.withEndAction {
                    binding.startButton.visibility = View.INVISIBLE
                    binding.fabNextPage.visibility = View.VISIBLE
                }.start()
            }
        }
    }
}