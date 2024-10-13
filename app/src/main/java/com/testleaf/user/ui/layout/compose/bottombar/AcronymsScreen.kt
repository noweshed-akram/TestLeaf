package com.testleaf.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.testleaf.user.R
import com.testleaf.user.domain.models.Acronyms
import com.testleaf.user.domain.models.Course
import com.testleaf.user.ui.component.ChipItemView
import com.testleaf.user.ui.component.InfoBannerCard
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.theme.ColorAccent
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.StrokeColor
import com.testleaf.user.ui.theme.Typography
import com.testleaf.user.ui.theme.WhiteColor
import com.testleaf.user.viewmodel.AsesmntViewModel
import com.testleaf.user.viewmodel.UserViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun AcronymsScreen(
    userViewModel: UserViewModel,
    asesmntViewModel: AsesmntViewModel,
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    var acronymList by rememberSaveable {
        mutableStateOf(emptyList<Acronyms>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList(50)
        courseList = emptyList()

        asesmntViewModel.coursesData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("AssessmentScreen: ", "Loading..")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("AssessmentScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                courseList = courseList + Course(docId = "001", name = "All") + it as List<Course>
                Log.d("AssessmentScreen: ", courseList.toString())
            }
        }
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getAcronyms()

        asesmntViewModel.acronymsData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("AcronymsScreen: ", "Loading..")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("AcronymsScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                acronymList = it as List<Acronyms>
                Log.d("AcronymsScreen: ", acronymList.toString())
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        if (acronymList.isNotEmpty()) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = false),
                onRefresh = {

                }) {

                Column {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(
                            items = courseList
                        ) { index, course ->
                            ChipItemView(course.name) {}
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(
                            items = acronymList
                        ) { index, acronym ->

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        shape = RoundedCornerShape(8.dp),
                                        color = StrokeColor
                                    )
                                    .clickable {

                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .background(color = ColorAccent, shape = CircleShape)
                                    ) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data("")
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = "",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(48.dp)
                                                .padding(8.dp)
                                                .clip(CircleShape),
                                            error = painterResource(id = R.drawable.ic_acronyms),
                                            colorFilter = ColorFilter.tint(color = PrimaryColor)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .weight(1.0f)
                                            .padding(vertical = 8.dp),
                                        verticalArrangement = Arrangement.SpaceAround
                                    ) {

                                        Text(
                                            modifier = Modifier.padding(5.dp),
                                            text = acronym.name,
                                            style = Typography.titleLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryColor,
                                            maxLines = 1
                                        )

                                        Text(
                                            modifier = Modifier.padding(5.dp),
                                            text = acronym.phrase,
                                            style = Typography.bodyMedium,
                                            color = Color.Black,
                                            maxLines = 1
                                        )

                                    }
                                }
                            }

                        }
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

        Spacer(modifier = Modifier.height(12.dp))

    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetToastUtil.SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }
}