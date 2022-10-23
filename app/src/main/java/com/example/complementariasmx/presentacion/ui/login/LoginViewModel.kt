package com.example.complementariasmx.presentacion.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complementariasmx.data.firebase.FireBaseRepository
import com.example.complementariasmx.data.repository.Repository
import com.example.complementariasmx.di.CoroutineDispatchers
import com.example.complementariasmx.domain.models.remote.login.UsersResponse
import com.example.complementariasmx.domain.state.ApiState
import com.example.complementariasmx.presentacion.ui.util.NetworkHelper
import com.example.complementariasmx.presentacion.ui.util.ViewModelStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val fireBaseRepository: FireBaseRepository,
    private val networkHelper: NetworkHelper,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val repository: Repository
) :
    ViewModel() {

    private val TAG = this.toString()
    private val _loginStatus = MutableLiveData<ViewModelStatus>()
    val loginStatus: LiveData<ViewModelStatus>
        get() = _loginStatus


    private val _userResponse = MutableLiveData<ApiState<List<UsersResponse>>>()
    val usersResponse: LiveData<ApiState<List<UsersResponse>>>
        get() = _userResponse

    fun login(password: String, email: String) {
        viewModelScope.launch(coroutineDispatchers.io) {
            withContext(coroutineDispatchers.main) {
                _userResponse.value = ApiState.Loading()
            }
            if (!networkHelper.isNetworkConnected()) {
                withContext(coroutineDispatchers.main) {
                    _userResponse.value = ApiState.ErrorNetwork()
                }
                return@launch
            }
            try {
                val response = repository.getAllUsers()
                if (searchUser("klein*#%*", "jimmie@gmail.com", response)) {
                    withContext(coroutineDispatchers.main) {
                        _userResponse.value = ApiState.Success(response)
                        return@withContext
                    }
                } else {
                    withContext(coroutineDispatchers.main) {
                        _userResponse.value = ApiState.Error(Throwable())
                    }
                }
            } catch (e: Throwable) {
                withContext(coroutineDispatchers.main) {
                    _userResponse.value = ApiState.Error(e)
                }
            }
        }
    }

    private fun searchUser(
        password: String,
        email: String,
        allUsers: List<UsersResponse>
    ): Boolean {
        var isValidUser = false
        allUsers.forEach {
            if (it.email == email && it.password == password) {
                isValidUser = true
                return@forEach
            }
        }
        Log.w("ANDROID", isValidUser.toString())
        return isValidUser
    }

    fun lognIn(email: String, password: String) {
        if (!networkHelper.isNetworkConnected()) return
        _loginStatus.value = ViewModelStatus.LOADING
        fireBaseRepository.signIn(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _loginStatus.value = ViewModelStatus.SUCCES
                return@addOnCompleteListener
            }
            _loginStatus.value = ViewModelStatus.ERROR
            Log.w(TAG, "Usuario y contraseña icorrecto")
        }
    }

}