package com.awsprep.user.ui.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.awsprep.user.R
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.GreyColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.SecondaryColor
import com.awsprep.user.ui.theme.publicSansFamily

/**
 * Created by Md. Noweshed Akram on 9/12/23.
 */
@Composable
fun AlertDialog(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    @DrawableRes dialogIcon: Int,
    drawableTint: Color = PrimaryColor,
    title: String = "Title",
    message: String = "This is a message!",
    onPositiveBtnPressed: () -> Unit,
    onNegativeBtnPressed: () -> Unit,
) {
    Dialog(
        onDismissRequest = { openDialogCustom.value },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier.background(Color.White)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        )
                )

                Image(
                    painter = painterResource(id = dialogIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(
                        color = drawableTint
                    ),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(70.dp)
                        .fillMaxWidth(),
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title.trim(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = message.trim(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        fontFamily = publicSansFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Black,
                    )
                }
                //.......................................................................
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(ColorAccent),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextButton(
                        modifier = Modifier.weight(1.0f),
                        onClick = {
                            onNegativeBtnPressed()
                            openDialogCustom.value = false
                        }) {

                        Text(
                            "Not Now",
                            fontFamily = publicSansFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(36.dp)
                            .width(1.dp)
                            .background(
                                color = GreyColor.copy(.2f),
                                shape = CircleShape
                            )
                    )

                    TextButton(
                        modifier = Modifier.weight(1.0f),
                        onClick = {
                            onPositiveBtnPressed()
                            openDialogCustom.value = false
                        }) {
                        Text(
                            "Yes",
                            fontFamily = publicSansFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = SecondaryColor,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    AlertDialog(
        openDialogCustom = mutableStateOf(false),
        dialogIcon = R.drawable.ic_notification,
        onPositiveBtnPressed = {},
        onNegativeBtnPressed = {})
}