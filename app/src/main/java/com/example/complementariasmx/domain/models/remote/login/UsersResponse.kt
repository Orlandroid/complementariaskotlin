package com.example.complementariasmx.domain.models.remote.login

data class UsersResponse(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: Name,
    val address: Address,
    val phone: String,
)

data class Address(
    val geolocation: Geolocation,
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String
)

data class Geolocation(
    val lat: String,
    val long: String
)

data class Name(
    val firstname: String,
    val lastname: String
)