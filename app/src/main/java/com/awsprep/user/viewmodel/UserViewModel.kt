package com.awsprep.user.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.awsprep.user.domain.models.ResponseState
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.domain.models.User
import com.awsprep.user.domain.models.UserState
import com.awsprep.user.domain.usecase.UserUseCase
import com.awsprep.user.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by noweshedakram on 29/7/23.
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    private val app: Application,
    private val userUseCase: UserUseCase
) : AndroidViewModel(app) {

    val isUserAuthenticated get() = userUseCase.isUserAuthenticatedInFirebase

    private val _userData = MutableStateFlow(UserState())
    val userData: StateFlow<UserState> = _userData

    private val _resultData = MutableStateFlow(ResponseState())
    val resultData: StateFlow<ResponseState> = _resultData

    fun getUserData() {
        userUseCase.getUserData().onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(isLoading = true)
                }

                is Resource.Error -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(user: User) {
        userUseCase.updateUser(user).onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(isLoading = true)
                }

                is Resource.Error -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProfilePic(imageUri: Uri) {
        userUseCase.updateProfilePic(imageUri).onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(isLoading = true)
                }

                is Resource.Error -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    Log.d("getUserData: ", it.data.toString())
                    _userData.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getTestResult() {
        viewModelScope.launch {
            userUseCase.getTestResult().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun insertTestResult(
        testResult: TestResult
    ) {
        viewModelScope.launch {
            userUseCase.insertTestResult(testResult).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("insertTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("insertTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("insertTestResult: ", it.data.toString())
                        _resultData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun logOut() {
        viewModelScope.launch {
            userUseCase.logOut()
        }
    }

}