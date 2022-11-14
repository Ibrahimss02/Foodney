package com.raion.foodney.ui.welcomeScreens.signIn

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.raion.foodney.MainActivity
import com.raion.foodney.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        binding.tvDaftar.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                LoginState.SUCCESS -> {
                    startActivity(Intent(activity, MainActivity::class.java))
                    requireActivity().finish()
                }
                LoginState.FAILED -> {
                    Toast.makeText(requireContext(), viewModel.signInMessages, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

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
                viewModel.signIn(email,password)
            }
        }

        return binding.root
    }
}