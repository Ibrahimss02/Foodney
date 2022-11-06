package com.raion.foodney.ui.welcomeScreens.splashScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raion.foodney.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        lifecycleScope.launch {
            delay(2000)
            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment())
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}