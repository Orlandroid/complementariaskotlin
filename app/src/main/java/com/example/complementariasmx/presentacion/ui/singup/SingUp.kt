package com.example.complementariasmx.presentacion.ui.singup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.complementariasmx.databinding.FragmentSingUpBinding
import com.example.complementariasmx.presentacion.ui.util.sendToast
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
    ): View {
        _binding = FragmentSingUpBinding.inflate(layoutInflater,container,false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {

        }
        createUserWithEmailAndPassword()
    }


    private fun createUserWithEmailAndPassword() {
        binding.buttonSingUp.setOnClickListener {
            if (areEqualPassword()) {
                auth.createUserWithEmailAndPassword(getUser(), getPassword())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            sendToast(requireContext(), "Usurio creado correctamente")
                            findNavController().popBackStack()
                        } else
                            Log.d(TAG, it.exception.toString())
                    }

            } else
                sendToast(requireContext(), "Las 2 contraseñas debe de ser iguales")
        }
    }

    private fun getUser(): String = binding.txtUser.editText?.text.toString()

    private fun getPassword(): String = binding.txtPassword.editText?.text.toString()

    private fun getPasswordSure(): String = binding.txtPasswordSure.editText?.text.toString()

    private fun areEqualPassword(): Boolean = getPassword() == getPasswordSure()

}