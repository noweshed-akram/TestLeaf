package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.awsprep.user.domain.models.ResponseState
import com.awsprep.user.domain.usecase.AsesmntUseCase
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
class AsesmntViewModel @Inject constructor(
    private val app: Application,
    private val asesmntUseCase: AsesmntUseCase
) : AndroidViewModel(app) {

    private val _coursesData = MutableStateFlow(ResponseState())
    val coursesData: StateFlow<ResponseState> = _coursesData

    private val _chaptersData = MutableStateFlow(ResponseState())
    val chaptersData: StateFlow<ResponseState> = _chaptersData

    private val _sectionsData = MutableStateFlow(ResponseState())
    val sectionsData: StateFlow<ResponseState> = _sectionsData

    fun getCourseList() {
        viewModelScope.launch {
            asesmntUseCase.getCourseList().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getCourseList: ", it.data.toString())
                        _coursesData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getCourseList: ", it.data.toString())
                        _coursesData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getCourseList: ", it.data.toString())
                        _coursesData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getChapterList(courseId: String) {
        viewModelScope.launch {
            asesmntUseCase.getChapterList(courseId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getChapterList: ", it.data.toString())
                        _chaptersData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getChapterList: ", it.data.toString())
                        _chaptersData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getChapterList: ", it.data.toString())
                        _chaptersData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getSectionList(courseId: String, chapterId: String) {
        viewModelScope.launch {
            asesmntUseCase.getSectionList(courseId, chapterId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getSectionList: ", it.data.toString())
                        _sectionsData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getSectionList: ", it.data.toString())
                        _sectionsData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getSectionList: ", it.data.toString())
                        _sectionsData.value = ResponseState(data = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}