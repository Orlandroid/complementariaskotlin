package com.example.complementariasmx.repository

import com.example.complementariasmx.firebase.FireBaseSource
import javax.inject.Inject

class FireBaseRepository @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun createUser(email: String, password: String) = fireBaseSource.createUser(email, password)

    fun signIn(email: String, password: String) = fireBaseSource.singIn(email, password)


    
}