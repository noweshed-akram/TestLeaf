package com.awsprep.user.ui.layout.compose.bottombar

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.awsprep.user.R
import com.awsprep.user.navigation.ContentNavScreen
import com.awsprep.user.ui.component.ImageButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.PrimaryColorLight
import com.awsprep.user.viewmodel.UserViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    navController: NavController, userViewModel: UserViewModel
) {

    val context = LocalContext.current

    var inputName by rememberSaveable { mutableStateOf("") }
    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPhone by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var inputAddress by rememberSaveable { mutableStateOf("") }

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
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_person)
        )

        Text(text = inputName)

        Text(text = inputEmail)

        Spacer(modifier = Modifier.height(16.dp))

        ImageButton(
            onClick = {
                navController.navigate(ContentNavScreen.EditProfile.route)
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
            buttonText = "Update Profile",
            backgroundColor = PrimaryColorLight,
            fontColor = Color.White
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