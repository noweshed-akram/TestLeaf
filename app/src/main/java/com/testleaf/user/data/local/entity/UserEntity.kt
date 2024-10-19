package com.testleaf.user.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Noweshed on 15/10/24.
 */
@Entity(
    tableName = "UserEntity",
    indices = [Index(value = ["userId"], unique = true)]
)
data class UserEntity(
    @PrimaryKey
    val userId: Int,
    val name: String,
    val email: String,
    val isEmailVerified: Int,
    val countryCode: String,
    val phoneNumber: String,
    val birthDate: String,
    val gender: String,
    val address: String,
    val profileAvatar: String,
    val accessToken: String,
    val expiresIn: Int,
    val isActive: Int,
    val createdAt: String,
    val updatedAt: String
)