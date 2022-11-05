package com.raion.foodney.ui.welcomeScreens.onBoarding.screen.second

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentFirstScreenBinding
import com.raion.foodney.databinding.FragmentSecondScreenBinding


class SecondScreenFragment : Fragment() {

    private lateinit var binding: FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

}