package com.testleaf.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testleaf.user.domain.models.ResponseState
import com.testleaf.user.domain.usecase.ApiUseCase
import com.testleaf.user.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@HiltViewModel
class QuesViewModel @Inject constructor(
    private val app: Application,
    private val apiUseCase: ApiUseCase
) : AndroidViewModel(app) {

    val TAG = "QuesViewModel"

    private val _questionResponse = MutableStateFlow(ResponseState())
    val questionResponse: StateFlow<ResponseState> = _questionResponse

    fun getQuestionList(limit: Int) {
        viewModelScope.launch {
            apiUseCase.getQuestionList(limit).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d(TAG, "loading")
                        _questionResponse.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, it.message.toString())
                        _questionResponse.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d(TAG, it.data.toString())
                        _questionResponse.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}