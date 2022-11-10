package com.raion.foodney.ui.mainScreens.warungList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentWarungListBinding
import com.raion.foodney.ui.adapter.OnClickWarungMissionListener
import com.raion.foodney.ui.adapter.WarungMissionAdapter
import com.raion.foodney.ui.mainScreens.MainViewModel

class WarungListFragment : Fragment() {

    private lateinit var binding: FragmentWarungListBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWarungListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = WarungMissionAdapter(OnClickWarungMissionListener {
            // TODO: NAVIGATE TO DETAIL WARUNG OR MISSION
        })
        binding.rvMission.adapter = adapter
        binding.rvWarungRecomendation.adapter = adapter


        return binding.root
    }
}