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
import com.awsprep.user.viewmodel.UserViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        askPermissions()

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

                    MainScreen(
                        navController = navController,
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        asesmntViewModel = asesmntViewModel
                    )
                }
            }
        }
    }

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            mapViewModel.getDeviceLocation(fusedLocationProviderClient)
//        }
//    }
//
//    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
//        ContextCompat.checkSelfPermission(
//            this, ACCESS_FINE_LOCATION
//        ) -> {
//            mapViewModel.getDeviceLocation(fusedLocationProviderClient)
//        }
//
//        else -> {
//            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
//        }
//    }
}