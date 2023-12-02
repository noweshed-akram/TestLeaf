package com.awsprep.user.ui.layout.compose.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.domain.models.User
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.navigation.Graph
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.utils.AppConstant.DATE_TIME_FORMAT
import com.awsprep.user.utils.Resource
import com.awsprep.user.utils.getCurrentDateTime
import com.awsprep.user.utils.toString
import com.awsprep.user.viewmodel.AuthViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetInfo
import kotlinx.coroutines.launch

/**
 * Created by noweshedakram on 17/7/23.
 */
@Composable
fun EmailRegisterScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    var inputName by rememberSaveable { mutableStateOf("") }
    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            authViewModel.firebaseUser.collect {
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
                    authViewModel.sendEmailVerification()
                    navController.navigate(Graph.HOME)
                }
            }
        }
    }

    when (val sendEmailVerificationResponse = authViewModel.sendEmailVerificationResponse) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> {
            val isVerificationEmailSent = sendEmailVerificationResponse.data
            if (isVerificationEmailSent == true) {
                SweetInfo(
                    message = "Verification Email Sent. Please Verify Your Email",
                    padding = PaddingValues(10.dp)
                )
            }
        }

        is Resource.Error -> sendEmailVerificationResponse.apply {
            SweetError(message = message.toString(), padding = PaddingValues(10.dp))
            LaunchedEffect(message) {
                print(message)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome to",
            textAlign = TextAlign.Start,
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AWS Test Prep",
            textAlign = TextAlign.Start,
            style = Typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create an account to continue",
            textAlign = TextAlign.Start,
            style = Typography.bodySmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputName,
            label = {
                Text(text = "User Name *")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "name"
                )
            },
            onValueChange = { value ->
                inputName = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputPassword,
            label = {
                Text(text = "Password *")
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "password"
                )
            },
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

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            onClick = {
                if (inputEmail.isNotEmpty() && inputName.isNotEmpty() && inputPassword.isNotEmpty()) {
                    authViewModel.signUpWithEmailAndPassword(
                        inputEmail, inputPassword, user = User(
                            name = inputName,
                            email = inputEmail,
                            createdAt = getCurrentDateTime().toString(DATE_TIME_FORMAT),
                            updatedAt = getCurrentDateTime().toString(DATE_TIME_FORMAT)
                        )
                    )
                } else {
                    showError = true
                    errorMsg = "Fill up the required field."
                }
            },
            buttonText = "Sign Up",
            backgroundColor = PrimaryColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Already Have an Account?")

            TextButton(onClick = {
                navController.navigate(AuthScreen.EmailSignIn.route)
            }) {
                Text(text = "Sign In")
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