package com.raion.foodney.ui.welcomeScreens.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.raion.foodney.MainActivity
import com.raion.foodney.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment : Fragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        lifecycleScope.launch {
            if (firebaseAuth.currentUser != null) {
                delay(2000)
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                delay(2000)
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToOnBoardingFragment())
            }
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }
}