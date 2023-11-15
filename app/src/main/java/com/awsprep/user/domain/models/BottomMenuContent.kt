package com.awsprep.user.domain.models

import androidx.annotation.DrawableRes

/**
 * Created by Md. Noweshed Akram on 14/11/23.
 */
data class BottomMenuContent(
    val title: String,
    val route: String,
    @DrawableRes
    val defaultIcon: Int,
    @DrawableRes
    val selectIcon: Int,
)
