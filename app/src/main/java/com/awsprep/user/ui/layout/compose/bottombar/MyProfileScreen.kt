package com.awsprep.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.awsprep.user.R
import com.awsprep.user.domain.models.TestResult
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.BarChartView
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SetsItemView
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.UserViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun MyProfileScreen(
    navController: NavController, userViewModel: UserViewModel
) {

    var inputName by rememberSaveable { mutableStateOf("") }
    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPhone by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    var resultList by rememberSaveable {
        mutableStateOf(emptyList<TestResult>())
    }

    LaunchedEffect(key1 = true) {

        userViewModel.userData.collect {
            if (it.isLoading) {
                showProgress = true
                Log.d("EmailSignScreen: ", "Loading")
            }
            if (it.error.isNotBlank()) {
                showProgress = false
                showError = true
                errorMsg = it.error
                Log.d("EmailSignScreen: ", it.error)
                userViewModel.getUserData()
            }
            it.data?.let {
                showProgress = false
                inputName = it.name
                inputEmail = it.email
                inputPhone = it.phone
                imageUrl = it.image
            }
        }

    }

    LaunchedEffect(key1 = true) {

        userViewModel.getTestResult()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(text = "My Profile", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.background(
                color = ColorAccent,
                shape = RoundedCornerShape(8.dp)
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(2.dp, PrimaryColor, CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_person)
            )

            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                Text(text = inputName, style = Typography.bodyLarge)

                Text(text = inputEmail, style = Typography.bodySmall)
            }

            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    navController.navigate(ContentNavScreen.EditProfile.route)
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "edit",
                    modifier = Modifier
                        .size(48.dp)
                        .background(SecondaryColor, CircleShape)
                        .padding(8.dp),
                    tint = Color.White
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Review Q's", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        SetsItemView(
            setsIcon = R.drawable.ic_review_ques,
            title = "Review Questions",
            subTitle = "explore review questions from your test"
        ) {
            navController.navigate(ContentNavScreen.ReviewQues.route)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Test Summary", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        BarChartView(
            resultList = resultList
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Last five test summary",
            textAlign = TextAlign.Center,
            style = Typography.bodySmall,
            color = GreyColor,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            onClick = {
                navController.navigate(ContentNavScreen.ResultDashboard.route)
            },
            buttonText = "Result Dashboard",
            backgroundColor = WhiteColor,
            fontColor = PrimaryColor,
            borderStrokeColor = PrimaryColor
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