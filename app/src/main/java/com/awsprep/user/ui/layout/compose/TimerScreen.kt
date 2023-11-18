package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awsprep.user.domain.models.Question
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.viewmodel.QuesViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun TimerScreen(
    navController: NavController,
    quesViewModel: QuesViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    LaunchedEffect(key1 = true) {
        quesViewModel.getQuestions(
            "gAIzFo3oMkeA5vtMdtHd",
            "SPMy3zbDGh1grrxSE242",
            "L0HhAjw2STqk8gvCYhbB"
        )

        quesViewModel.questionData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("SectionScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("SectionScreen: ", it.error)
            }
            it.data?.let {
                showProgress = false
                questionList = it as List<Question>
                quesViewModel.questionOrder = questionList
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PrimaryButton(
            onClick = {
                navController.navigate(ContentNavScreen.Test.route)
            },
            buttonText = "Start"
        )
    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }

}