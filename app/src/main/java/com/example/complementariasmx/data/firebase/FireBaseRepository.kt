package com.example.complementariasmx.data.firebase

import javax.inject.Inject

class FireBaseRepository @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun createUser(email: String, password: String) = fireBaseSource.createUser(email, password)

    fun signIn(email: String, password: String) = fireBaseSource.singIn(email, password)

}