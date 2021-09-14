package com.example.complementariasmx.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FireBaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    fun createUser(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun singIn(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)


}