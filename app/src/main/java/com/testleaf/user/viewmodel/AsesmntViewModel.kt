package com.testleaf.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.testleaf.user.domain.models.ResponseState
import com.testleaf.user.domain.usecase.AsesmntUseCase
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

    private val _setsData = MutableStateFlow(ResponseState())
    val setData: StateFlow<ResponseState> = _setsData

    private val _subSetsData = MutableStateFlow(ResponseState())
    val subSetData: StateFlow<ResponseState> = _subSetsData

    private val _acronymsData = MutableStateFlow(ResponseState())
    val acronymsData: StateFlow<ResponseState> = _acronymsData

    private val _definitionsData = MutableStateFlow(ResponseState())
    val definitionsData: StateFlow<ResponseState> = _definitionsData

    fun getCourseList(limit: Long) {
        viewModelScope.launch {
            asesmntUseCase.getCourseList(limit = limit).onEach {
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
                        _coursesData.value = ResponseState(dataList = it.data)
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
                        _chaptersData.value = ResponseState(dataList = it.data)
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
                        _sectionsData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getSetList() {
        viewModelScope.launch {
            asesmntUseCase.getSetList().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getSetList: ", it.data.toString())
                        _setsData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getSetList: ", it.data.toString())
                        _setsData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getSetList: ", it.data.toString())
                        _setsData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getSubSetList(setId: String, subSetId: String) {
        viewModelScope.launch {
            asesmntUseCase.getSubSetList(setId, subSetId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getSubSetList: ", it.data.toString())
                        _subSetsData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getSubSetList: ", it.data.toString())
                        _subSetsData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getSubSetList: ", it.data.toString())
                        _subSetsData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getAcronyms() {
        viewModelScope.launch {
            asesmntUseCase.getAcronyms().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getAcronyms: ", it.data.toString())
                        _acronymsData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getAcronyms: ", it.data.toString())
                        _acronymsData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getAcronyms: ", it.data.toString())
                        _acronymsData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getDefinition() {
        viewModelScope.launch {
            asesmntUseCase.getDefinition().onEach {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("getDefinition: ", it.data.toString())
                        _definitionsData.value = ResponseState(isLoading = true)
                    }

                    is Resource.Error -> {
                        Log.d("getDefinition: ", it.data.toString())
                        _definitionsData.value = ResponseState(error = it.message ?: "")
                    }

                    is Resource.Success -> {
                        Log.d("getDefinition: ", it.data.toString())
                        _definitionsData.value = ResponseState(dataList = it.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}