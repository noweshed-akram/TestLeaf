package com.awsprep.user.domain.models

import com.google.firebase.auth.FirebaseUser

/**
 * Created by noweshedakram on 17/7/23.
 */
data class AuthState(
    val data: FirebaseUser? = null,
    val error: String = "",
    val isLoading: Boolean = false
)