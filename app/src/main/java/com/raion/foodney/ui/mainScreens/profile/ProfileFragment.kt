package com.raion.foodney.ui.mainScreens.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.raion.foodney.R
import com.raion.foodney.WelcomeActivity
import com.raion.foodney.databinding.FragmentProfileBinding
import com.raion.foodney.ui.adapter.OnClickWarungMissionListener
import com.raion.foodney.ui.adapter.WarungMissionAdapter
import com.raion.foodney.ui.mainScreens.MainViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.completedMissionRv.adapter = WarungMissionAdapter(OnClickWarungMissionListener {
            Toast.makeText(requireContext(), "Completed Mission $it", Toast.LENGTH_SHORT).show()
        })

        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finish()
        }


        return binding.root
    }
}