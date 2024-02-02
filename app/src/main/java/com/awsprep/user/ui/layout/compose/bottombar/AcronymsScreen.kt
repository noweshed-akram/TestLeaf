package com.awsprep.user.ui.layout.compose.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.Question
import com.awsprep.user.ui.component.InfoBannerCard
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.UserViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun AcronymsScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var questionList by rememberSaveable {
        mutableStateOf(emptyList<Question>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        if (questionList.isNotEmpty()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = false),
                onRefresh = {

                }) {
                LazyColumn(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(
                        items = questionList
                    ) { index, question ->

                    }
                }
            }
        } else {
            InfoBannerCard(
                icon = R.drawable.ic_acronyms,
                titleText = "No data found!",
                infoText = "You will find more Acronyms later.",
                strokeColor = StrokeColor,
                bgColor = WhiteColor,
                onCardClick = {}
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