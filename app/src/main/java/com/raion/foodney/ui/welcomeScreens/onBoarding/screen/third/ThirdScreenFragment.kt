package com.raion.foodney.ui.welcomeScreens.onBoarding.screen.third

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentFirstScreenBinding
import com.raion.foodney.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.btnNext.setOnClickListener {
            /*TODO : NAVIGATE TO SIGN UP*/
        }
    }

}