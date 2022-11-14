package com.raion.foodney.ui.welcomeScreens.signIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.raion.foodney.models.Profile

class SignInViewModel : ViewModel() {

    private val _signInState = MutableLiveData<LoginState>()
    val signInState: MutableLiveData<LoginState>
        get() = _signInState

    var signInMessages = ""

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    var user: Profile? = null


    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _signInState.value = LoginState.SUCCESS
                Log.d("SignInViewModel", "Login Success")
            }
            .addOnFailureListener {
                signInMessages = it.message.toString()
                _signInState.value = LoginState.FAILED
                Log.d("SignInViewModel", "Login Gagal")
            }
    }
}

enum class LoginState {
    SUCCESS, FAILED
}