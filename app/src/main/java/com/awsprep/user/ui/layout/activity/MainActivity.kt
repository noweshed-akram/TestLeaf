package com.awsprep.user.ui.layout.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.awsprep.user.ui.layout.compose.MainScreen
import com.awsprep.user.ui.theme.AwsPrepTheme
import com.awsprep.user.viewmodel.AsesmntViewModel
import com.awsprep.user.viewmodel.AuthViewModel
import com.awsprep.user.viewmodel.EntityViewModel
import com.awsprep.user.viewmodel.QuesViewModel
import com.awsprep.user.viewmodel.UserViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            Log.d("FCM", token.toString())
        })

        super.onCreate(savedInstanceState)

        setContent {
            AwsPrepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel by viewModels()
                    val userViewModel: UserViewModel by viewModels()
                    val asesmntViewModel: AsesmntViewModel by viewModels()
                    val quesViewModel: QuesViewModel by viewModels()
                    val entityViewModel: EntityViewModel by viewModels()

                    MainScreen(
                        scrollBehavior = scrollBehavior,
                        navController = navController,
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        asesmntViewModel = asesmntViewModel,
                        quesViewModel = quesViewModel,
                        entityViewModel = entityViewModel
                    )
                }
            }
        }
    }

}