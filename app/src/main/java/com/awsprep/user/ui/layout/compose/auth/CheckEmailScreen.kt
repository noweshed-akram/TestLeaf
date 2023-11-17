package com.awsprep.user.ui.layout.compose.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.awsprep.user.R
import com.awsprep.user.navigation.AuthScreen
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.theme.PrimaryColor

/**
 * Created by Md. Noweshed Akram on 17/11/23.
 */
@Composable
fun CheckEmailScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_email),
                contentDescription = "check_email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Check Your Email",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PrimaryColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We have sent a password recovery instruction\n" +
                        "to your email.",
                textAlign = TextAlign.Center
            )
        }

        PrimaryButton(
            onClick = {
                navController.navigate(AuthScreen.EmailSignIn.route)
            },
            buttonText = "Login",
            backgroundColor = PrimaryColor
        )

    }
}