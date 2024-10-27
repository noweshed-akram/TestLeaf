package com.testleaf.user.ui.layout.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.testleaf.user.R
import com.testleaf.user.domain.models.TestResult
import com.testleaf.user.ui.component.BarChartView
import com.testleaf.user.ui.component.PrimaryButton
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.theme.ColorAccent
import com.testleaf.user.ui.theme.GreyColor
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.SecondaryColor
import com.testleaf.user.ui.theme.WhiteColor
import com.testleaf.user.ui.theme.publicSansFamily
import com.testleaf.user.viewmodel.UserViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil
import com.testleaf.user.viewmodel.EntityViewModel
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun MyProfileScreen(
    userViewModel: UserViewModel,
    entityViewModel: EntityViewModel,
    onEditBtnClick: () -> Unit,
    onDashboardBtnClick: () -> Unit,
    onClickSubs: () -> Unit
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
        entityViewModel.getUserData().collectLatest { user ->
            inputName = user.name
            inputEmail = user.email
            inputPhone = user.phoneNumber
            imageUrl = user.profileAvatar
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
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {

//        Text(text = "My Profile", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Profile",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

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
                Text(
                    text = inputName,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    text = inputEmail,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            IconButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    onEditBtnClick()
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "edit",
                    modifier = Modifier
                        .size(36.dp)
                        .background(SecondaryColor, CircleShape)
                        .padding(8.dp),
                    tint = Color.White
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Subscription",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .background(
                    color = ColorAccent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    onClickSubs()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = "",
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .border(1.dp, PrimaryColor, CircleShape),
                contentScale = ContentScale.Inside,
                error = painterResource(id = R.drawable.ic_person_plus)
            )

            Column(
                modifier = Modifier.weight(1.0f)
            ) {
                Text(
                    text = "Become A Pro Member",
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    text = "Unlock advanced feature & more...",
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp),
                error = painterResource(id = R.drawable.ic_arrow_forward),
                colorFilter = ColorFilter.tint(color = PrimaryColor)
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Test Summary",
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            BarChartView(
                resultList = resultList.take(5)
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Last five test summary",
            textAlign = TextAlign.Center,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = GreyColor,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            onClick = {
                onDashboardBtnClick()
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