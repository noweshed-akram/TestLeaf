package com.awsprep.user.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Md. Noweshed Akram on 19/11/23.
 */
data class NavItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)