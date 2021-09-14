package com.example.complementariasmx.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.complementariasmx.repository.FireBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val fireBaseRepository: FireBaseRepository) :
    ViewModel() {


    private val TAG = "LoginViewModel"

    fun lognIn(email: String, password: String) =
        fireBaseRepository.signIn(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.w(TAG, "Logeado correctamente")
            } else {
                Log.w(TAG, "Usuario y contraseña icorrecto")
            }
        }


}