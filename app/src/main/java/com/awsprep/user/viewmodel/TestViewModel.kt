package com.awsprep.user.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.awsprep.user.domain.models.Question
import com.awsprep.user.domain.models.QuestionIndexData

/**
 * Created by Md. Noweshed Akram on 26/11/23.
 */
class TestViewModel : ViewModel() {

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

    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    private val _multipleChoiceResponse = mutableStateMapOf<String, MutableList<String>>()
    val multipleChoiceResponse: Map<String, List<String>>
        get() = _multipleChoiceResponse

    fun onMultipleChoiceResponse(selected: Boolean, quesId: String, answer: String) {

        val currentList = _multipleChoiceResponse[quesId] ?: mutableStateListOf()

        if (answer in currentList) {
            currentList.remove(answer)
        } else {
            currentList.add(answer)
        }
        _multipleChoiceResponse[quesId] = currentList

        _isNextEnabled.value = getIsNextEnabled()
    }


    private val _singleChoiceResponse = mutableStateMapOf<String, String>()
    val singleChoiceResponse: Map<String, String>
        get() = _singleChoiceResponse

    fun onSingleChoiceResponse(quesId: String, answer: String) {
        _singleChoiceResponse[quesId] = answer
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

    private fun getIsNextEnabled(): Boolean {
        return questionIndex < questionOrder.size
    }

    private fun createQuestionIndexData(): QuestionIndexData {
        return QuestionIndexData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowSubmitButton = questionIndex == questionOrder.size - 1,
            question = questionOrder[questionIndex],
        )
    }

}