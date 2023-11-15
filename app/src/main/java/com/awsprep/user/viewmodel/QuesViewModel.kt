package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    private var questionIndex = 0

    /**
     * Returns true if the ViewModel handled the back press (i.e., it went back one question)
     */


    // ----- Responses exposed as State -----

    private val _multipleChoiceResponse = mutableStateListOf<String>()
    val multipleChoiceResponse: List<String>
        get() = _multipleChoiceResponse

    private val _singleChoiceResponse = mutableStateOf("")
    val singleChoiceResponse: String
        get() = _singleChoiceResponse.value


    private val questionOrder: List<QuestionType> = listOf(
        QuestionType.SINGLE_CHOICE,
        QuestionType.MULTIPLE_CHOICE
    )

    private val _screenData = mutableStateOf(createScreenData())
    val screenData: ScreenData?
        get() = _screenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onMultipleChoiceResponse(selected: Boolean, answer: String) {
        if (selected) {
            _multipleChoiceResponse.add(answer)
        } else {
            _multipleChoiceResponse.remove(answer)
        }
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onSingleChoiceResponse(answer: String) {
        _singleChoiceResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
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
        _screenData.value = createScreenData()
    }

    fun onDonePressed(onSurveyComplete: () -> Unit) {
        // Here is where you could validate that the requirements of the survey are complete
        onSurveyComplete()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            QuestionType.SINGLE_CHOICE -> _singleChoiceResponse.value.isNotEmpty()
            QuestionType.MULTIPLE_CHOICE -> _multipleChoiceResponse.isNotEmpty()
        }
    }

    private fun createScreenData(): ScreenData {
        return ScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            questionType = questionOrder[questionIndex],
        )
    }

}

enum class QuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICE
}

data class ScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val questionType: QuestionType
)