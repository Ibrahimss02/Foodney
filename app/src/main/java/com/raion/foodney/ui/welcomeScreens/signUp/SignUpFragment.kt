package com.raion.foodney.ui.welcomeScreens.signUp

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raion.foodney.R
import com.raion.foodney.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        binding.tvMasuk.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        binding.btnNext.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val name = binding.edtUsername.text.toString()

            if (email.isEmpty()){
                binding.edtEmail.error = "Email is required!"
                binding.edtEmail.requestFocus()
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = "Please input valid email!"
                binding.edtEmail.requestFocus()
            }
            else if (password.isEmpty()){
                binding.edtPassword.error = "Password is required!"
                binding.edtPassword.requestFocus()
            }
            else if (password.length < 6){
                binding.edtPassword.error = "Minimal password length should be 6 characters!"
                binding.edtPassword.requestFocus()
            }
            else {
                viewModel.signUp(name,email,password)
            }
        }


        return binding.root
    }
}