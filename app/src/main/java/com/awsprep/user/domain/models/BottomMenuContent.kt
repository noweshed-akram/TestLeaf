package com.awsprep.user.domain.models

import androidx.annotation.DrawableRes

data class BottomMenuContent(
    val title: String,
    val route: String,
    @DrawableRes
    val defaultIcon: Int,
    @DrawableRes
    val selectIcon: Int,
)
