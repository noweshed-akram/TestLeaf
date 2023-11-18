package com.awsprep.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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

    private var questionIndex = 0

    var questionOrder: List<Question> = mutableStateListOf(
        Question(
            quesId = "1",
            ques = "Test 1",
            ans = listOf("A"),
            optionA = "A",
            optionB = "B",
            optionC = "C",
            optionD = "D",
            optionE = ""
        ),
        Question(
            quesId = "2",
            ques = "Test 2",
            ans = listOf("A", "D"),
            optionA = "A",
            optionB = "B",
            optionC = "C",
            optionD = "D",
            optionE = "E"
        )
    )

    fun getQuestions(
        courseId: String,
        chapterId: String,
        sectionId: String
    ) {

        Log.d("questionOrder: ", questionOrder.toString())
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

    // ----- Responses exposed as State -----

    private val _multipleChoiceResponse = mutableStateListOf<String>()
    val multipleChoiceResponse: List<String>
        get() = _multipleChoiceResponse

    fun onMultipleChoiceResponse(selected: Boolean, answer: String) {
        if (selected) {
            _multipleChoiceResponse.add(answer)
        } else {
            _multipleChoiceResponse.remove(answer)
        }
        _isNextEnabled.value = getIsNextEnabled()
    }

    private val _singleChoiceResponse = mutableStateOf("")
    val singleChoiceResponse: String
        get() = _singleChoiceResponse.value

    fun onSingleChoiceResponse(answer: String) {
        _singleChoiceResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
    }

    private val _questionIndexData = mutableStateOf(createQuestionIndexData())
    val questionIndexData: QuestionIndexData?
        get() = _questionIndexData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

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
        _questionIndexData.value = createQuestionIndexData()
    }

    fun onDonePressed(onTestComplete: () -> Unit) {
        onTestComplete()
    }

    private fun getIsNextEnabled(): Boolean {
        return questionIndex < questionOrder.size // TODO fix to check of question is answered
    }

    private fun createQuestionIndexData(): QuestionIndexData {
        return QuestionIndexData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            question = questionOrder[questionIndex],
        )
    }

}

data class QuestionIndexData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val question: Question
)