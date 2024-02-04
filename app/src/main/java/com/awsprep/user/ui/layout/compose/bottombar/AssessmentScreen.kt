package com.awsprep.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.awsprep.user.R
import com.awsprep.user.domain.models.Course
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.domain.models.Set
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SetsItemView
import com.awsprep.user.ui.component.VerticalGrid
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.utils.AppConstant.COLL_COURSES
import com.awsprep.user.utils.AppConstant.COLL_SETS
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AssessmentScreen(
    asesmntViewModel: AsesmntViewModel,
    onCourseItemClick: (ExamMetaData) -> Unit,
    onSetItemClick: (ExamMetaData) -> Unit,
    onAllCourseBtnClick: () -> Unit,
    onReviewQuesClick: () -> Unit
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        painterResource(id = R.drawable.ic_error_icon),
        painterResource(id = R.drawable.ic_error_icon)
    )

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    var setList by rememberSaveable {
        mutableStateOf(emptyList<Set>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList(6)

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
                courseList = it as List<Course>
                Log.d("AssessmentScreen: ", courseList.toString())
            }
        }
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getSetList()

        asesmntViewModel.setData.collect {
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
                setList = it as List<Set>
                Log.d("AssessmentScreen: ", setList.toString())
            }
        }
    }

//    LaunchedEffect(Unit) {
//        while (true) {
//            yield()
//            delay(5000)
//            pagerState.animateScrollToPage(
//                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
//            )
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 12.dp)
    ) {

//        Text(
//            modifier = Modifier.padding(horizontal = 12.dp),
//            text = "Quick Quiz",
//            style = Typography.titleLarge
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        HorizontalPager(
//            count = imageSlider.size,
//            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 12.dp),
//            modifier = Modifier
//                .height(114.dp)
//                .fillMaxWidth()
//        ) { page ->
//            Card(
//                shape = RoundedCornerShape(12.dp),
//                modifier = Modifier
//                    .graphicsLayer {
//                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
//
//                        lerp(
//                            start = 0.85f,
//                            stop = 1f,
//                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                        ).also { scale ->
//                            scaleX = scale
//                            scaleY = scale
//                        }
//
//                        alpha = lerp(
//                            start = 0.5f,
//                            stop = 1f,
//                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                        )
//                    }
//            ) {
//                Image(
//                    painter = imageSlider[page],
//                    contentDescription = stringResource(R.string.image_slider),
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
//
//        HorizontalPagerIndicator(
//            pagerState = pagerState,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(16.dp),
//            indicatorWidth = 24.dp,
//            indicatorShape = CircleShape,
//            indicatorHeight = 4.dp,
//            activeColor = PrimaryColor,
//            inactiveColor = StrokeColor,
//            spacing = 4.dp
//        )

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Courses",
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        VerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = 2
        ) {
            courseList.forEachIndexed { index, course ->
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            1.dp,
                            StrokeColor,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {

                            val examMetaData = ExamMetaData(
                                examName = course.name,
                                examType = COLL_COURSES,
                                courseId = course.docId
                            )

                            onCourseItemClick(examMetaData)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .background(color = StrokeColor, shape = CircleShape)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(course.icon)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(52.dp)
                                    .padding(1.dp)
                                    .clip(CircleShape),
                                error = painterResource(id = R.drawable.ic_error_icon)
                            )
                        }

                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "${index + 1}. ${course.name}",
                            style = Typography.titleMedium,
                            color = Color.Black,
                            maxLines = 2
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                                .background(color = ColorAccent)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "10+ Chapters",
                                style = Typography.bodySmall,
                                color = Color.Black,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(
            modifier = Modifier.padding(horizontal = 12.dp),
            onClick = {
                onAllCourseBtnClick()
            },
            buttonText = "Explore More Course",
            backgroundColor = WhiteColor,
            fontColor = PrimaryColor,
            borderStrokeColor = PrimaryColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Sets",
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height((setList.size * 92).dp),
            userScrollEnabled = false,
        ) {
            items(setList) { set ->

                SetsItemView(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    setsIcon = R.drawable.ic_edit,
                    title = set.name,
                    subTitle = "100+ Practice Test Sets"
                ) {

                    val examMetaData = ExamMetaData(
                        examName = set.name,
                        examType = COLL_SETS,
                        setId = set.setId,
                        setFlag = set.flag
                    )

                    onSetItemClick(examMetaData)

                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Review Q's",
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        SetsItemView(
            modifier = Modifier.padding(horizontal = 12.dp),
            setsIcon = R.drawable.ic_review_ques,
            title = "Review Questions",
            subTitle = "explore your review questions for next Exam"
        ) {
            onReviewQuesClick()
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