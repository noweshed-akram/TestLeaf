package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.ResultListItem
import com.awsprep.user.viewmodel.UserViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 1/12/23.
 */
@Composable
fun ResultDashboard(
    navController: NavController,
    userViewModel: UserViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var resultList by rememberSaveable {
        mutableStateOf(emptyList<TestResult>())
    }

    LaunchedEffect(key1 = true) {
        userViewModel.resultData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("getTestResult: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("getTestResult: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                resultList = it as List<TestResult>
                Log.d("getTestResult: ", resultList.toString())
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .wrapContentSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = resultList
        ) { result ->
            ResultListItem(
                examType = result.testType,
                examName = result.testName,
                totalQs = result.totalQs,
                correctAns = result.correctAnswered,
                testDate = result.createdAt,
                status = result.status
            )
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