package com.testleaf.user.ui.layout.compose

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.testleaf.user.R
import com.testleaf.user.domain.models.User
import com.testleaf.user.ui.component.PrimaryButton
import com.testleaf.user.ui.component.ProgressBar
import com.testleaf.user.ui.theme.SecondaryColor
import com.testleaf.user.ui.theme.publicSansFamily
import com.testleaf.user.viewmodel.UserViewModel
import com.google.modernstorage.photopicker.PhotoPicker
import com.talhafaki.composablesweettoast.util.SweetToastUtil

/**
 * Created by Md. Noweshed Akram on 10/11/23.
 */
@androidx.core.os.BuildCompat.PrereleaseSdkCheck
@Composable
fun EditProfileScreen(
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
                userViewModel.getUserData()
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
            .padding(12.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(132.dp)
            ) {
                AsyncImage(
                    model = if (selectedImageUri != null) selectedImageUri else imageUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(4.dp, SecondaryColor, CircleShape),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_person)
                )

                IconButton(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {
                        photoPicker.launch(
                            PhotoPicker.Args(
                                PhotoPicker.Type.IMAGES_ONLY,
                                1
                            )
                        )
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_photo_camera),
                        contentDescription = "edit_profile",
                        modifier = Modifier
                            .size(40.dp)
                            .background(SecondaryColor, CircleShape)
                            .padding(8.dp),
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "User Name",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputName,
//            label = {
//                Text(text = "User Name")
//            },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_person),
//                    contentDescription = "name"
//                )
//            },
            onValueChange = { value ->
                inputName = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            readOnly = true,
            enabled = false,
            value = inputEmail,
//            label = {
//                Text(text = "Email")
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Phone",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputPhone,
//            label = {
//                Text(text = "Phone")
//            },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_phone),
//                    contentDescription = "phone"
//                )
//            },
            onValueChange = { value ->
                inputPhone = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Address",
            textAlign = TextAlign.Start,
            fontFamily = publicSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputAddress,
//            label = {
//                Text(text = "Address")
//            },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_flag),
//                    contentDescription = "address"
//                )
//            },
            onValueChange = { value ->
                inputAddress = value
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
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
            buttonText = "Save Changes"
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