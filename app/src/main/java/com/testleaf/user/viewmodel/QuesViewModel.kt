package com.testleaf.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testleaf.user.domain.models.Feedback
import com.testleaf.user.domain.models.Question
import com.testleaf.user.domain.models.ResponseState
import com.testleaf.user.domain.usecase.QuesUseCase
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
    private val quesUseCase: QuesUseCase
) : AndroidViewModel(app) {

    private val _questionData = MutableStateFlow(ResponseState())
    val questionData: StateFlow<ResponseState> = _questionData


    private val _reviewQuesData = MutableStateFlow(ResponseState())
    val reviewQuesData: StateFlow<ResponseState> = _reviewQuesData

    fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String,
        limit: Long
    ) {
        viewModelScope.launch {
            quesUseCase.getQuestions(courseId, chapterId, sectionId, limit).onEach {
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
                        _questionData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getQuestions(
        setId: String,
        subSetId: String,
        sectionId: String,
    ) {
        viewModelScope.launch {
            quesUseCase.getQuestions(setId, subSetId, sectionId).onEach {
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
                        _questionData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun addToReviewQues(
        question: Question
    ) {
        viewModelScope.launch {
            quesUseCase.addToReviewQues(question).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("addToReviewQues: ", it.data.toString())
                        _questionData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("addToReviewQues: ", it.data.toString())
                        _questionData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("addToReviewQues: ", it.data.toString())
                        _questionData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getReviewQues() {
        viewModelScope.launch {
            quesUseCase.getReviewQues().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun deleteReviewQues(quesId: String) {
        viewModelScope.launch {
            quesUseCase.deleteReviewQues(quesId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getQuestions: ", it.data.toString())
                        _reviewQuesData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun sendQuesFeedback(
        feedback: Feedback
    ) {
        viewModelScope.launch {
            quesUseCase.sendQuesFeedback(feedback).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("sendQuesFeedback: ", it.data.toString())
                        _questionData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("sendQuesFeedback: ", it.data.toString())
                        _questionData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("sendQuesFeedback: ", it.data.toString())
                        _questionData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}