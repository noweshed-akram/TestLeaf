package com.awsprep.user.ui.layout.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.awsprep.user.R
import com.awsprep.user.domain.models.SubsFeatures
import com.awsprep.user.ui.component.PrimaryButton
import com.awsprep.user.ui.theme.ColorAccent
import com.awsprep.user.ui.theme.ErrorColor
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.TextColor
import com.awsprep.user.ui.theme.Typography
import com.awsprep.user.ui.theme.WhiteColor
import com.awsprep.user.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubscribeScreen(
    userViewModel: UserViewModel
) {

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = false
    )

    val subsFeatures = listOf(
        SubsFeatures(featureName = "Standard Lessons", freeMember = true, proMember = true),
        SubsFeatures(featureName = "Personalized Lessons", freeMember = false, proMember = true),
        SubsFeatures(featureName = "Access All Courses", freeMember = false, proMember = true),
        SubsFeatures(featureName = "No ads and wait limit", freeMember = false, proMember = true),
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "get the best deal for you!", style = Typography.titleMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Row {

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = PrimaryColor
                            )
                            .weight(1.0f)
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = PrimaryColor.copy(.2f),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                                .padding(8.dp),
                            text = "Monthly!",
                            color = PrimaryColor,
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "BDT 341/m",
                            style = Typography.bodySmall,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )

                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = PrimaryColor
                            )
                            .weight(1.0f)
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = PrimaryColor.copy(.2f),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                                .padding(8.dp),
                            text = "Yearly!",
                            color = PrimaryColor,
                            style = Typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "BDT 4099/y",
                            style = Typography.bodySmall,
                            maxLines = 1,
                            textAlign = TextAlign.Center
                        )

                    }

                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "subscribe today. cancel anytime later",
                    style = Typography.bodySmall,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )

                PrimaryButton(
                    onClick = {

                    },
                    buttonText = "continue",
                    backgroundColor = WhiteColor,
                    fontColor = PrimaryColor,
                    borderStrokeColor = PrimaryColor
                )

            }
        }) {

        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Become a pro member today and experience advanced lesson's with more practice's...",
                style = typography.titleMedium,
                color = PrimaryColor,
                textAlign = TextAlign.Justify
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = ColorAccent,
                        shape = MaterialTheme.shapes.small
                    ),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1.0f),
                    text = "You will get",
                    style = typography.bodyMedium,
                    color = PrimaryColor,
                    textAlign = TextAlign.Start
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                    text = "Free",
                    style = typography.bodyMedium,
                    color = PrimaryColor,
                    textAlign = TextAlign.Justify
                )

                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                    text = "Pro",
                    style = typography.bodyMedium,
                    color = PrimaryColor,
                    textAlign = TextAlign.Justify
                )
            }

            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                userScrollEnabled = false,
            ) {
                items(subsFeatures) { feature ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .weight(1.0f),
                                text = feature.featureName.trim(),
                                style = typography.bodyMedium,
                                color = TextColor,
                                textAlign = TextAlign.Start
                            )

                            Icon(
                                imageVector = if (feature.freeMember) {
                                    ImageVector.vectorResource(id = R.drawable.ic_check)
                                } else {
                                    ImageVector.vectorResource(
                                        id = R.drawable.ic_close
                                    )
                                },
                                contentDescription = "check",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                tint = if (feature.freeMember) PrimaryColor else ErrorColor
                            )

                            Icon(
                                imageVector = if (feature.proMember) {
                                    ImageVector.vectorResource(id = R.drawable.ic_check)
                                } else {
                                    ImageVector.vectorResource(
                                        id = R.drawable.ic_close
                                    )
                                },
                                contentDescription = "check",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                tint = if (feature.proMember) PrimaryColor else ErrorColor
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(1.dp)
                                .background(
                                    color = PrimaryColor,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(width = 1.dp, shape = RoundedCornerShape(8.dp), color = PrimaryColor)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = PrimaryColor.copy(.2f),
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                        .padding(8.dp),
                    text = "14 Days Free!",
                    color = PrimaryColor,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "BDT 341/m",
                    style = Typography.bodySmall,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        text = "BDT 5099/y",
                        style = TextStyle(textDecoration = TextDecoration.LineThrough),
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        text = "BDT 4099/y",
                        style = Typography.bodySmall,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    PrimaryButton(
                        onClick = {

                        },
                        buttonText = "start 14 days free trial",
                        backgroundColor = PrimaryColor
                    )
                }

            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "14 days free trial. Then BDT 3999 per year. cancel anytime",
                style = Typography.bodySmall,
                maxLines = 1,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        coroutineScope.launch {
                            if (modalSheetState.isVisible)
                                modalSheetState.hide()
                            else
                                modalSheetState.show() //(ModalBottomSheetValue.Expanded)
                        }
                    },
                text = "VIEW ALL PLANS",
                style = Typography.bodyMedium,
                color = PrimaryColor,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }


}