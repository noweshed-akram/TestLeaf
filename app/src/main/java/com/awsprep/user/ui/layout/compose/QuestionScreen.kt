package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.QuestionItem
import com.awsprep.user.viewmodel.QuesViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 11/11/23.
 */
@Composable
fun QuestionScreen(
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
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    items = questionList
                ) { question ->
                    QuestionItem(question = question)
                }
            }
        }

    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }

}