package com.awsprep.user.ui.layout.compose.bottombar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.viewmodel.UserViewModel

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@Composable
fun PracticesScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Practice Screen",
            style = Typography.labelSmall
        )
    }
}