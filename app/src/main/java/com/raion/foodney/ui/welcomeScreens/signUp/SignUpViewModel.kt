package com.raion.foodney.ui.welcomeScreens.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.raion.foodney.models.Profile

class SignUpViewModel : ViewModel() {

    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: MutableLiveData<SignUpState>
        get() = _signUpState

    var signUpMessages = ""

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://foodney-49058-default-rtdb.firebaseio.com/").reference


    fun signUp(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _signUpState.value = SignUpState.SUCCESS
                Log.d("SignUpViewModel", "Daftar Berhasil")

                // ADD USER KE REALTIME DB
                val newUser = Profile(
                    uid = it.user!!.uid,
                    name = name,
                    email = email
                )

                firebaseDatabase.child("users").child(newUser.uid).setValue(newUser)
                    .addOnSuccessListener {
                        Log.d("SignUpViewModel", "Tambah ke DB Berhasil")
                    }
                    .addOnFailureListener {
                        Log.d("SignUpViewModel", "Tambah ke DB Gagal")
                    }
            }
            .addOnFailureListener {
                signUpMessages = it.message.toString()
                _signUpState.value = SignUpState.FAILED
                Log.d("SignUpViewModel", "Daftar Gagal")
            }
    }
}

enum class SignUpState {
    SUCCESS, FAILED
}