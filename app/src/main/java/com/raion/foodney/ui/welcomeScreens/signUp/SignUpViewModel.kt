package com.raion.foodney.ui.welcomeScreens.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.raion.foodney.models.Profile

class SignUpViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase =
        FirebaseDatabase.getInstance("https://foodney-49058-default-rtdb.firebaseio.com/").reference


    fun signUp(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                /* TODO : TOAST TANDA SIGNUP BERHASIL*/
                Log.d("SignUpViewModel", "Daftar Berhasil")

                // ADD USER KE REALTIME DB
                val newUser = Profile(
                    id = it.user!!.uid,
                    name = name,
                    email = email
                )

                firebaseDatabase.child("user").setValue(newUser)
                    .addOnSuccessListener {
                        Log.d("SignUpViewModel", "Tambah ke DB Berhasil")
                    }
                    .addOnFailureListener {
                        Log.d("SignUpViewModel", "Tambah ke DB Gagal")
                    }
            }
            .addOnFailureListener {
                /* TODO : TOAST TANDA SIGNUP GAGAL*/
                Log.d("SignUpViewModel", "Daftar Gagal")
            }
    }
}