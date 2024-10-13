package com.testleaf.user.ui.component

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.testleaf.user.R
import com.testleaf.user.ui.layout.compose.StatusTextComposable
import com.testleaf.user.ui.theme.ColorAccent
import com.testleaf.user.ui.theme.GreyColor
import com.testleaf.user.ui.theme.PrimaryColor
import com.testleaf.user.ui.theme.SecondaryColor

/**
 * Created by Md. Noweshed Akram on 12/18/2023.
 */
@Composable
fun TestSubmitAlertDialog(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    @DrawableRes dialogIcon: Int,
    drawableTint: Color = PrimaryColor,
    title: String = "please check before submit",
    message: String = "Total Questions",
    totalQs: String = "0",
    quesAnswered: String = "0",
    skippedQues: String = "0",
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
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        color = GreyColor,
                        fontWeight = FontWeight.SemiBold,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = message.trim() + " $totalQs",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    StatusTextComposable(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .weight(1.0f),
                        status = "Question Tried",
                        progress = quesAnswered
                    )

                    StatusTextComposable(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .weight(1.0f),
                        status = "Skipped",
                        progress = skippedQues
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                            "Goto Questions",
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
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
                            "Submit Anyway",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
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
fun TestSubmitAlertDialogPreview() {
    TestSubmitAlertDialog(
        openDialogCustom = mutableStateOf(false),
        dialogIcon = R.drawable.ic_warning,
        onPositiveBtnPressed = {},
        onNegativeBtnPressed = {})
}