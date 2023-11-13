package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.awsprep.user.domain.models.Question
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

    private var questionIndex = 0

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one question)
     */
    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete
        onSurveyComplete()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            SurveyQuestion.FREE_TIME -> _freeTimeResponse.isNotEmpty()
            SurveyQuestion.SUPERHERO -> _superheroResponse.value != null
            SurveyQuestion.LAST_TAKEAWAY -> _takeawayResponse.value != null
            SurveyQuestion.FEELING_ABOUT_SELFIES -> _feelingAboutSelfiesResponse.value != null
            SurveyQuestion.TAKE_SELFIE -> _selfieUri.value != null
        }
    }


}

data class ScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val question: Question
)