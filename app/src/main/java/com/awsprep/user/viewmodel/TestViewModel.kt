package com.awsprep.user.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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


    private val _multipleChoiceResponse =
        mutableStateMapOf<String, MutableState<MutableList<String>>>()
    val multipleChoiceResponse: Map<String, State<List<String>>>
        get() = _multipleChoiceResponse

    @SuppressLint("MutableCollectionMutableState")
    fun onMultipleChoiceResponse(selected: Boolean, quesId: String, answer: String) {
        if (selected) {
            val answers = listOf(answer)
            if (!_multipleChoiceResponse.containsKey(quesId)) {
                _multipleChoiceResponse[quesId] = mutableStateOf(answers.toMutableList())
            } else {
                _multipleChoiceResponse[quesId]?.value?.add(answer)
            }
        } else {
            _multipleChoiceResponse[quesId]?.value?.remove(answer)
        }
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