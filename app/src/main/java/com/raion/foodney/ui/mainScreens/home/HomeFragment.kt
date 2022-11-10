package com.raion.foodney.ui.mainScreens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raion.foodney.databinding.FragmentHomeBinding
import com.raion.foodney.ui.adapter.WarungMissionAdapter
import com.raion.foodney.ui.mainScreens.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.viewModel = viewModel
        binding.recyclerView.adapter = WarungMissionAdapter()









        return binding.root
    }
}