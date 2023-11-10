package com.awsprep.user.ui.layout.compose.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.ui.component.ImageButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.PrimaryColorLight
import com.awsprep.user.utils.Resource
import com.awsprep.user.viewmodel.AuthViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetInfo

/**
 * Created by noweshedakram on 21/7/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    var inputEmail by rememberSaveable { mutableStateOf("") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    when (val sendPasswordResetEmailResponse = authViewModel.sendPasswordResetEmailResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isPasswordResetEmailSent = sendPasswordResetEmailResponse.data
            if (isPasswordResetEmailSent == true) {
                SweetInfo(message = "Password Reset Email Sent. Please Check Inbox/Spam Folder!")
                LaunchedEffect(isPasswordResetEmailSent) {
                    navController.navigate(AuthScreen.EmailSignIn.route)
                }
            }
        }

        is Resource.Error -> sendPasswordResetEmailResponse.apply {
            SweetError(message = message.toString())
            LaunchedEffect(message) {
                print(message)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Password Reset",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryColorLight
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Please type your register valid email to get a password reset email.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputEmail,
            label = {
                Text(text = "Email *")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "email"
                )
            },
            onValueChange = { value ->
                inputEmail = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        ImageButton(
            onClick = {
                if (inputEmail.isNotEmpty()) {
                    authViewModel.sendPasswordResetEmail(inputEmail)
                } else {
                    showError = true
                    errorMsg = "Please Fill the required filed"
                }
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_login),
            buttonText = "Reset Password",
            backgroundColor = PrimaryColorLight,
            fontColor = Color.White
        )

        if (showProgress) {
            ProgressBar()
        }

        if (showError) {
            showError = false
            SweetError(message = errorMsg, padding = PaddingValues(10.dp))
        }

    }
}