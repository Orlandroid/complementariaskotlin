package com.example.complementariasmx.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.complementariasmx.R
import com.example.complementariasmx.databinding.FragmentSingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SingUp : Fragment() {

    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = "SIGNUP"
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingUpBinding.inflate(layoutInflater)
        binding.buttonSingUp.setOnClickListener {
            if (areEqualPassword()) {
                auth.createUserWithEmailAndPassword(getUser(), getPassword())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Usuario Creado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_singUp_to_login)
                        } else
                            Log.d(TAG, it.exception.toString())
                    }

            } else
                Toast.makeText(
                    requireContext(),
                    "Las 2 contraseñas deben de ser iguales",
                    Toast.LENGTH_SHORT
                ).show()
        }
        return binding.root
    }

    private fun getUser(): String = binding.txtUser.editText?.text.toString()

    private fun getPassword(): String = binding.txtPassword.editText?.text.toString()

    private fun getPasswordSure(): String = binding.txtPasswordSure.editText?.text.toString()

    private fun areEqualPassword(): Boolean = getPassword() == getPasswordSure()

}