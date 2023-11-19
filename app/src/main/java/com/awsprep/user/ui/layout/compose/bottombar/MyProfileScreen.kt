package com.awsprep.user.ui.layout.compose.bottombar

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.awsprep.user.R
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.component.SetsItemView
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.StrokeColor
import com.awsprep.user.ui.theme.Typography
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

    LaunchedEffect(key1 = true) {
        userViewModel.getUserData()

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Text(text = "My Profile", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.background(color = ColorAccent, shape = RoundedCornerShape(8.dp)),
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

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    navController.navigate(ContentNavScreen.EditProfile.route)
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "edit",
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(16))
                        .background(SecondaryColor)
                        .padding(8.dp)
                        .size(24.dp),
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
            subTitle = "Find review questions from your test"
        ) {
            navController.navigate(ContentNavScreen.ReviewQues.route)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Test Summary", style = Typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    StrokeColor,
                    RoundedCornerShape(8.dp)
                )
                .clickable {
                    navController.navigate(ContentNavScreen.Chapters.route)
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Random",
                    style = Typography.labelMedium,
                    color = GreyColor,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "AWS Trusted Advisor",
                    style = Typography.titleMedium,
                    color = PrimaryColor,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .background(color = ColorAccent)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "Correct Answer",
                                style = Typography.labelMedium,
                                color = GreyColor,
                                maxLines = 1
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "51",
                                style = Typography.bodyLarge,
                                color = Color.Black,
                                maxLines = 1
                            )
                        }

                        Column {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "Correct Answer",
                                style = Typography.labelMedium,
                                color = GreyColor,
                                maxLines = 1
                            )

                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = "51",
                                style = Typography.bodyLarge,
                                color = Color.Black,
                                maxLines = 1
                            )
                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = "Complete",
                            style = Typography.bodyLarge,
                            color = PrimaryColor,
                            maxLines = 1
                        )

                    }

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