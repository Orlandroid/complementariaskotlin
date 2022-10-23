package com.example.complementariasmx.data.api

import com.example.complementariasmx.domain.models.remote.login.UsersResponse
import retrofit2.http.GET

interface FakeService {

    @GET("users.json")
    suspend fun getAllUsers(): List<UsersResponse>

}