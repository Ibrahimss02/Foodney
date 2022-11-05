package com.raion.foodney.ui.welcomeScreens.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raion.foodney.databinding.FragmentOnBoardingBinding
import com.raion.foodney.ui.adapter.OnBoardingViewPagerAdapter
import com.raion.foodney.ui.welcomeScreens.onBoarding.screen.first.FirstScreenFragment
import com.raion.foodney.ui.welcomeScreens.onBoarding.screen.second.SecondScreenFragment
import com.raion.foodney.ui.welcomeScreens.onBoarding.screen.third.ThirdScreenFragment

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listOfFragment = listOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = OnBoardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle
        )

        adapter.apply {
            setAllFragments(listOfFragment)
            binding.viewPager.adapter = adapter
        }

        binding.apply {
            indicator.attachTo(viewPager)
        }
    }
}