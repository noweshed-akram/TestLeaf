package com.awsprep.user.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awsprep.user.ui.theme.SecondaryColor

/**
 * Created by Md. Noweshed Akram on 7/20/2023.
 */
@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = SecondaryColor)
    }
}