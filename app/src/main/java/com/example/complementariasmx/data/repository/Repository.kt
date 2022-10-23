package com.example.complementariasmx.data.repository


import com.example.complementariasmx.data.api.FakeService
import com.example.complementariasmx.data.firebase.FireBaseSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val fakeService: FakeService,
    private val fireBaseSource: FireBaseSource
) {


    fun createUser(email: String, password: String) = fireBaseSource.createUser(email, password)

    fun signIn(email: String, password: String) = fireBaseSource.singIn(email, password)

    suspend fun getAllUsers() = fakeService.getAllUsers()


}