package com.awsprep.user.ui.layout.compose

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
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
import com.awsprep.user.domain.models.User
import com.awsprep.user.ui.component.ImageButton
import com.awsprep.user.ui.component.ProgressBar
import com.awsprep.user.ui.theme.PrimaryColorLight
import com.awsprep.user.viewmodel.UserViewModel
import com.google.modernstorage.photopicker.PhotoPicker
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@androidx.core.os.BuildCompat.PrereleaseSdkCheck
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

    var inputName by rememberSaveable { mutableStateOf("") }
    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPhone by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    var inputAddress by rememberSaveable { mutableStateOf("") }

    var showProgress by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    val photoPicker = rememberLauncherForActivityResult(PhotoPicker()) { uri ->
        // uris contain the list of selected images & video
        Log.d("UserProfileScreen: ", uri.toString())
        if (uri.isNotEmpty()) {
            selectedImageUri = uri[0]
            userViewModel.updateProfilePic(selectedImageUri!!)
        }
    }

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
                inputAddress = it.address
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

        Box(modifier = Modifier.size(150.dp)) {
            AsyncImage(
                model = if (selectedImageUri != null) selectedImageUri else imageUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_person)
            )

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    photoPicker.launch(
                        PhotoPicker.Args(
                            PhotoPicker.Type.IMAGES_ONLY,
                            1
                        )
                    )
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                    contentDescription = "edit",
                    modifier = Modifier
                        .shadow(2.dp, RoundedCornerShape(40))
                        .background(PrimaryColorLight)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                        .size(30.dp)
                        .align(Alignment.BottomEnd),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputName,
            label = {
                Text(text = "User Name")
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
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputEmail,
            label = {
                Text(text = "Email")
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
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputPhone,
            label = {
                Text(text = "Phone")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "phone"
                )
            },
            onValueChange = { value ->
                inputPhone = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputAddress,
            label = {
                Text(text = "Address")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_flag),
                    contentDescription = "address"
                )
            },
            onValueChange = { value ->
                inputAddress = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ImageButton(
            onClick = {
                userViewModel.updateUser(
                    user = User(
                        name = inputName,
                        email = inputEmail,
                        phone = inputPhone,
                        address = inputAddress
                    )
                )
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