package com.testleaf.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testleaf.user.data.remote.model.req.LoginReq
import com.testleaf.user.domain.models.ResponseState
import com.testleaf.user.domain.usecase.ApiUseCase
import com.testleaf.user.utils.Resource
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.testleaf.user.data.remote.model.req.RegisterReq
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Noweshed on 12/10/24.
 */
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val app: Application,
    private val apiUseCase: ApiUseCase
) : AndroidViewModel(app) {

    private val _loginResponse = MutableStateFlow(ResponseState())
    val loginResponse: StateFlow<ResponseState> = _loginResponse

    private val _registrationResponse = MutableStateFlow(ResponseState())
    val registrationResponse: StateFlow<ResponseState> = _registrationResponse

    fun userRegistration(registerReq: RegisterReq) {
        val gson = Gson()
        val json = gson.toJson(registerReq)
        val authInfo = JsonParser.parseString(json).asJsonObject

        viewModelScope.launch {
            apiUseCase.userRegistration(authInfo).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("ApiViewModel: ", "loading")
                        _registrationResponse.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("ApiViewModel: error", it.message.toString())
                        _registrationResponse.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("ApiViewModel: success", it.data.toString())
                        _registrationResponse.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun userLogin(loginReq: LoginReq) {
        val gson = Gson()
        val json = gson.toJson(loginReq)
        val authInfo = JsonParser.parseString(json).asJsonObject

        viewModelScope.launch {
            apiUseCase.userLogin(authInfo).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("ApiViewModel: ", "loading")
                        _loginResponse.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("ApiViewModel: error", it.message.toString())
                        _loginResponse.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("ApiViewModel: success", it.data.toString())
                        _loginResponse.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}