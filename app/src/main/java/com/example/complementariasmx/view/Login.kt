package com.example.complementariasmx.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.complementariasmx.R
import com.example.complementariasmx.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val TAG = "LOGIN"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        FirebaseApp.initializeApp(requireContext())
        binding.buttonLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.txtUser.editText?.text.toString(),
                binding.txtPassord.editText?.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Bienbenido", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_login_to_menuPrincipal)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Usuario y contraseña icorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.txtSingUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_singUp)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}