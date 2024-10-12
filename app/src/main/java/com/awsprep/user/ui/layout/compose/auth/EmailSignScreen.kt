package com.awsprep.user.ui.layout.compose.auth

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awsprep.user.R
import com.awsprep.user.data.remote.model.req.LoginReq
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.publicSansFamily
import com.awsprep.user.viewmodel.ApiViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError

/**
 * Created by noweshedakram on 17/7/23.
 */
@Composable
fun EmailSignScreen(
    authViewModel: AuthViewModel,
    apiViewModel: ApiViewModel,
    onSuccessLogin: () -> Unit,
    onResetBtnClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = apiViewModel.authResponse) {
        apiViewModel.authResponse.collect {
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
                Log.d("EmailSignScreen: ", "Login Successful")
//                onSuccessLogin()
            }
        }
    }

//    LaunchedEffect(key1 = true) {
//        coroutineScope.launch {
//            authViewModel.firebaseUser.collect {
//                if (it.isLoading) {
//                    showProgress = true
//                    Log.d("EmailSignScreen: ", "Loading")
//                }
//                if (it.error.isNotBlank()) {
//                    showProgress = false
//                    showError = true
//                    errorMsg = it.error
//                    Log.d("EmailSignScreen: ", it.error)
//                }
//                it.data?.let {
//                    showProgress = false
//                    onSuccessLogin()
//                }
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .background(color = SecondaryColor, shape = CircleShape)
                    .width(96.dp)
                    .height(96.dp)
                    .padding(16.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_definition),
                contentDescription = "Stopwatch",
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome to",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Test Prep",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Please login to continue",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Email",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputEmail,
            textStyle = TextStyle(
                fontFamily = publicSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            placeholder = {
                Text(text = "e.g. abc@mail.com")
            },
//            label = {
//                Text(text = "Email *")
//            },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_email),
//                    contentDescription = "email"
//                )
//            },
            onValueChange = { value ->
                inputEmail = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputPassword,
            textStyle = TextStyle(
                fontFamily = publicSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            placeholder = {
                Text(text = "e.g. 123456")
            },
//            label = {
//                Text(text = "Password *")
//            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_password),
//                    contentDescription = "password"
//                )
//            },
            onValueChange = { value ->
                inputPassword = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(8.dp),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Forgot Password?",
                fontFamily = publicSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            TextButton(onClick = {
                onResetBtnClick()
            }) {
                Text(
                    text = "Reset Now",
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            onClick = {
                if (inputEmail.isNotEmpty() && inputPassword.isNotEmpty()) {
                    Log.d("EmailSignScreen: ", "Click Login Button")
                    apiViewModel.userLogin(
                        LoginReq(
                            email = inputEmail,
                            password = inputPassword
                        )
                    )
//                    authViewModel.signInWithEmailAndPassword(inputEmail, inputPassword)
                } else {
                    showError = true
                    errorMsg = "Please Fill the required filed"
                }
            },
            buttonText = "Login"
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't Have an Account?",
                fontFamily = publicSansFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )

            TextButton(onClick = {
                onNavigateToRegister()
            }) {
                Text(
                    text = "Sign Up",
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }

    if (showProgress) {
        ProgressBar()
    }

    if (showError) {
        showError = false
        SweetError(message = errorMsg, padding = PaddingValues(10.dp))
    }

}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmailSignScreenPreview() {
//    EmailSignScreen(
//        authViewModel = ,
//        onSuccessLogin = { },
//        onResetBtnClick = { },
//        onNavigateToRegister = { }
//    )
}