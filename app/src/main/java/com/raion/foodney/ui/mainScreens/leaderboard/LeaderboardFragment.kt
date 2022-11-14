package com.raion.foodney.ui.mainScreens.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentLeaderboardBinding
import com.raion.foodney.ui.adapter.LeaderboardAdapter
import com.raion.foodney.ui.mainScreens.MainViewModel

class LeaderboardFragment : Fragment() {

    private lateinit var binding: FragmentLeaderboardBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvLeaderboard.adapter = LeaderboardAdapter()

        return binding.root
    }
}