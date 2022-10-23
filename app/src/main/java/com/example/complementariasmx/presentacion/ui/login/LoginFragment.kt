package com.example.complementariasmx.presentacion.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.complementariasmx.databinding.FragmentLoginBinding
import com.example.complementariasmx.domain.state.ApiState
import com.example.complementariasmx.presentacion.main.AlertDialogs
import com.example.complementariasmx.presentacion.main.AlertDialogs.Companion.ERROR_MESSAGE
import com.example.complementariasmx.presentacion.ui.util.ViewModelStatus
import com.example.complementariasmx.presentacion.ui.util.sendToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        setUpUi()
        setUpObervers()
        return binding.root
    }

    private fun setUpUi() {
        with(binding) {
            buttonLogin.setOnClickListener {
                if (!areEmptyFiels()) {
                    viewModel.login(
                        password = binding.txtPassord.editText?.text.toString().trim(),
                        email = binding.txtUser.editText?.text.toString().trim()
                    )
                    return@setOnClickListener
                }
                sendToast(requireContext(), "Debes de llenar ambos campos")
            }
            txtSingUp.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginToSingUp()
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpObervers() {
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            when (it) {
                ViewModelStatus.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                ViewModelStatus.NETWORK_ERROR -> {
                    sendToast(requireContext(), "Error al registrar al usuario")
                    binding.progressBar.visibility = View.INVISIBLE
                }
                ViewModelStatus.SUCCES -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val action = LoginFragmentDirections.actionLoginToMenuPrincipal()
                    findNavController().navigate(action)
                }
                ViewModelStatus.ERROR -> {
                    sendToast(requireContext(), "Error al iniciar session con el usuario")
                    binding.progressBar.visibility = View.INVISIBLE
                }
                else -> {}
            }
        }
        viewModel.usersResponse.observe(viewLifecycleOwner) { apiState ->
            binding.progressBar.isVisible = apiState is ApiState.Loading
            when (apiState) {
                is ApiState.Success -> {
                    val action = LoginFragmentDirections.actionLoginToMenuPrincipal()
                    findNavController().navigate(action)
                    Log.w("ANDROID","SUCCCES")
                }
                is ApiState.Error -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE,"Error usuario o contraseña incorrecto")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                is ApiState.ErrorNetwork -> {
                    val dialog = AlertDialogs(ERROR_MESSAGE, "Verifica tu conexion de internet")
                    activity?.let { dialog.show(it.supportFragmentManager, "alertMessage") }
                }
                else -> {}
            }

        }
    }

    private fun areEmptyFiels(): Boolean {
        val userIsEmpty = binding.txtUser.editText?.text.toString().isEmpty()
        val passwordIdEmpty = binding.txtPassord.editText?.text.toString().isEmpty()
        return userIsEmpty or passwordIdEmpty
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}