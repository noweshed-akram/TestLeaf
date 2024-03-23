package com.awsprep.user.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.awsprep.user.R
import com.awsprep.user.ui.theme.PrimaryColor
import com.awsprep.user.ui.theme.publicSansFamily

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithArrow(
    scrollBehavior: TopAppBarScrollBehavior,
    title: String = "",
    titleColor: Color = Color.White,
    topBarColor: Color = PrimaryColor,
    backBtnColor: Color = Color.White,
    pressOnBack: () -> Unit
) {
    MediumTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(fraction = 1f)
            ) {
                Text(
                    text = title, color = titleColor,
                    fontFamily = publicSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = pressOnBack) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "go back",
                    tint = backBtnColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = topAppBarColors(
            containerColor = topBarColor,
            scrolledContainerColor = topBarColor
        ),
        scrollBehavior = scrollBehavior
    )

}