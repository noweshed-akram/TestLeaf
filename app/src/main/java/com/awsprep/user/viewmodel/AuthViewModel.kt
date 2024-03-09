package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.awsprep.user.domain.models.AuthState
import com.awsprep.user.domain.models.User
import com.awsprep.user.domain.usecase.AuthUseCase
import com.awsprep.user.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by noweshedakram on 14/7/23.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val app: Application,
    private val authUseCase: AuthUseCase
) : AndroidViewModel(app) {

    private val TAG = "AuthViewModel"
    private val _firebaseUser = MutableStateFlow(AuthState())
    val firebaseUser: StateFlow<AuthState> = _firebaseUser

    /**
     * Firebase Sign in Methods for Email and Password
     */
    var sendEmailVerificationResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))
    var sendPasswordResetEmailResponse by mutableStateOf<Resource<Boolean>>(Resource.Success(false))

    fun clearAuthState() {
        _firebaseUser.value = AuthState()
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        user: User
    ) {
        viewModelScope.launch {
            authUseCase.signUpWithEmailAndPassword(email, password, user).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d(TAG, "emailRegister: " + it.data.toString())
                        _firebaseUser.value = AuthState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "emailRegister: " + it.data.toString())
                        _firebaseUser.value = AuthState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "emailRegister: " + it.data.toString())
                        _firebaseUser.value = AuthState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Resource.Loading()
        sendEmailVerificationResponse = authUseCase.sendEmailVerification()
    }

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        sendPasswordResetEmailResponse = Resource.Loading()
        sendPasswordResetEmailResponse = authUseCase.sendPasswordResetEmail(email)
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.signInWithEmailAndPassword(email, password).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d(TAG, "loginWithEmail: " + it.data.toString())
                        _firebaseUser.value = AuthState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "loginWithEmail: " + it.message.toString())
                        _firebaseUser.value = AuthState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, "loginWithEmail: " + it.data.toString())
                        _firebaseUser.value = AuthState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    /**
     * End of
     * Firebase Sign in Methods for Email and Password
     */

}