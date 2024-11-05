package com.testleaf.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import com.testleaf.user.R
import com.testleaf.user.data.remote.model.response.CourseData
import com.testleaf.user.domain.models.ExamMetaData
import com.testleaf.user.ui.component.GridItemView
import com.testleaf.user.ui.component.PrimaryButton
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.component.SetsItemView
import com.testleaf.user.ui.component.VerticalGrid
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.StrokeColor
import com.testleaf.user.ui.theme.WhiteColor
import com.testleaf.user.ui.theme.publicSansFamily
import com.testleaf.user.utils.AppConstant.COLL_COURSES
import com.testleaf.user.viewmodel.AsesmntViewModel
import kotlinx.coroutines.delay
import java.lang.Thread.yield
import kotlin.math.absoluteValue

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

    val TAG = "AssessmentScreen"
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val pagerState = rememberPagerState(initialPage = 0)

    val imageSlider = listOf(
        "https://firebasestorage.googleapis.com/v0/b/awsprep-89123.appspot.com/o/banner_quiz.png?alt=media&token=dd852845-77e0-4571-a44e-a594eca43a60",
        "https://t3.ftcdn.net/jpg/01/95/16/90/360_F_195169024_R0XSeqsxvAcpdxVm0Grt3XFdhDWubwGs.jpg"
    )

    var courseList by rememberSaveable {
        mutableStateOf(emptyList<CourseData>())
    }

//    var setList by rememberSaveable {
//        mutableStateOf(emptyList<Set>())
//    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getCourseList()

        asesmntViewModel.courseResponse.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d(TAG, "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d(TAG, it.error)
            }
            it.dataList?.let {
                showProgress = false
                courseList = it as List<CourseData>
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(5000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Quick Quiz",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier
                .height(114.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                AsyncImage(
                    model = imageSlider[page],
                    contentDescription = "Profile picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_error_icon)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            indicatorWidth = 24.dp,
            indicatorShape = CircleShape,
            indicatorHeight = 4.dp,
            activeColor = PrimaryColor,
            inactiveColor = StrokeColor,
            spacing = 4.dp
        )

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Courses",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        VerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            columns = 2
        ) {
            courseList.forEachIndexed { index, course ->
                GridItemView(
                    title = "${index + 1}. ${course.courseName}",
                    subTitle = "10+ Chapters",
                    iconUrl = course.iconData?.url!!
                ) {
                    val examMetaData = ExamMetaData(
                        examName = course.courseName,
                        examType = COLL_COURSES,
                        courseId = course.id.toString()
                    )
                    onCourseItemClick(examMetaData)
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
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height((setList.size * 92).dp),
//            userScrollEnabled = false,
//        ) {
//            items(setList) { set ->
//
//                SetsItemView(
//                    modifier = Modifier.padding(horizontal = 12.dp),
//                    setsIcon = R.drawable.ic_edit,
//                    title = set.name,
//                    subTitle = "100+ Practice Test Sets"
//                ) {
//
//                    val examMetaData = ExamMetaData(
//                        examName = set.name,
//                        examType = COLL_SETS,
//                        setId = set.setId,
//                        setFlag = set.flag
//                    )
//
//                    onSetItemClick(examMetaData)
//
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = "Review Q's",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
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