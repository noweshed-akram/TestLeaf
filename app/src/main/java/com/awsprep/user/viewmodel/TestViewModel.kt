package com.awsprep.user.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.models.QuestionIndexData

/**
 * Created by Md. Noweshed Akram on 26/11/23.
 */
class TestViewModel() : ViewModel() {

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