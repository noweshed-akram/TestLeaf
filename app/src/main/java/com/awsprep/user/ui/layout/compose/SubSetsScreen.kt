package com.awsprep.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awsprep.user.domain.models.Course
import com.awsprep.user.domain.models.ExamMetaData
import com.awsprep.user.ui.component.GridItemView
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.publicSansFamily
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 18/11/23.
 */
@Composable
fun SubSetsScreen(
    asesmntViewModel: AsesmntViewModel,
    examMetaData: ExamMetaData,
    onSubsetItemClick: (ExamMetaData) -> Unit
) {

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var subSetList by rememberSaveable {
        mutableStateOf(emptyList<Course>())
    }

    LaunchedEffect(key1 = true) {
        asesmntViewModel.getSubSetList(examMetaData.setId!!, examMetaData.setFlag!!)

        asesmntViewModel.subSetData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("SubSetsScreen: ", "Loading..")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("SubSetsScreen: ", it.error)
            }
            it.dataList?.let {
                showProgress = false
                subSetList = it as List<Course>
                Log.d("SubSetsScreen: ", subSetList.toString())
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp)
    ) {

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = examMetaData.examName!!,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            maxLines = 1
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = { }) {

            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2)
            ) {
                items(subSetList.size) {
                    GridItemView(
                        title = "${it + 1}. ${subSetList[it].name}",
                        subTitle = "100+ Questions",
                        iconUrl = subSetList[it].icon
                    ) {
                        val xmMetaData = ExamMetaData(
                            examName = examMetaData.examName,
                            examType = examMetaData.examType,
                            setId = examMetaData.setId,
                            setFlag = examMetaData.setFlag,
                            subsetId = subSetList[it].docId
                        )

                        onSubsetItemClick(xmMetaData)
                    }

//                    Box(
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .clip(RoundedCornerShape(8.dp))
//                            .border(
//                                1.dp,
//                                StrokeColor,
//                                RoundedCornerShape(8.dp)
//                            )
//                            .clickable {
//
//                                val xmMetaData = ExamMetaData(
//                                    examName = examMetaData.examName,
//                                    examType = examMetaData.examType,
//                                    setId = examMetaData.setId,
//                                    setFlag = examMetaData.setFlag,
//                                    subsetId = subSetList[it].docId
//                                )
//
//                                onSubsetItemClick(xmMetaData)
//
//                            }
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .fillMaxHeight(),
//                            verticalArrangement = Arrangement.SpaceBetween
//                        ) {
//
//                            Box(
//                                modifier = Modifier
//                                    .padding(10.dp)
//                                    .background(color = StrokeColor, shape = CircleShape)
//                            ) {
//                                AsyncImage(
//                                    model = ImageRequest.Builder(LocalContext.current)
//                                        .data(subSetList[it].icon)
//                                        .crossfade(true)
//                                        .build(),
//                                    contentDescription = "",
//                                    contentScale = ContentScale.Fit,
//                                    modifier = Modifier
//                                        .size(52.dp)
//                                        .padding(1.dp)
//                                        .clip(CircleShape),
//                                    error = painterResource(id = R.drawable.ic_error_icon)
//                                )
//                            }
//
//                            Text(
//                                modifier = Modifier.padding(10.dp),
//                                text = "${it + 1}. ${subSetList[it].name}",
//                                style = Typography.titleMedium,
//                                color = Color.Black,
//                                maxLines = 2
//                            )
//
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
//                                    .background(color = ColorAccent)
//                            ) {
//                                Text(
//                                    modifier = Modifier.padding(10.dp),
//                                    text = "100+ Questions",
//                                    style = Typography.bodySmall,
//                                    color = Color.Black,
//                                    maxLines = 1
//                                )
//                            }
//                        }
//                    }
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