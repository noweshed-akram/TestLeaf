package com.awsprep.user.ui.layout.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.ui.layout.compose.MainScreen
import com.awsprep.user.ui.theme.AwsPrepTheme
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            AwsPrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel by viewModels()
                    val userViewModel: UserViewModel by viewModels()
                    val asesmntViewModel: AsesmntViewModel by viewModels()
                    val quesViewModel: QuesViewModel by viewModels()

                    MainScreen(
                        navController = navController,
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        asesmntViewModel = asesmntViewModel,
                        quesViewModel = quesViewModel
                    )
                }
            }
        }
    }

}