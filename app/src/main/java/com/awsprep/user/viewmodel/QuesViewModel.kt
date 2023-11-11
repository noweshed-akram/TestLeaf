package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.awsprep.user.domain.models.ResponseState
import com.awsprep.user.domain.usecase.QuesUseCase
import com.awsprep.user.utils.Resource
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
    private val quesUseCase: QuesUseCase
) : AndroidViewModel(app) {

    private val _questionData = MutableStateFlow(ResponseState())
    val questionData: StateFlow<ResponseState> = _questionData

    fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String
    ) {
        viewModelScope.launch {
            quesUseCase.getQuestions(courseId, chapterId, sectionId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _questionData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _questionData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _questionData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}