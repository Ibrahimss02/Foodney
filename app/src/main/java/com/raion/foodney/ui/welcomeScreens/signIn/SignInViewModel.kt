package com.raion.foodney.ui.welcomeScreens.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.raion.foodney.models.Profile

class SignInViewModel : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var user: Profile? = null


    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                /* TODO: TOAST UNTUK TANDA LOGIN SUKSES */
                Log.d("SignInViewModel", "Login Berhasil")
            }
            .addOnFailureListener {
                /* TODO: TOAST UNTUK TANDA LOGIN GAGAL */
                Log.d("SignInViewModel", "Login Gagal")
            }
    }
}