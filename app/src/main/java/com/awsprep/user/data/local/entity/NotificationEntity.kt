package com.awsprep.user.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Md. Noweshed Akram on 1/30/2024.
 */
@Entity(
    tableName = "Notification",
    indices = [Index(value = ["notificationId"], unique = true)]
)
data class NotificationEntity(
    @PrimaryKey
    val notificationId: Int,
    val title: String,
    val msg: String,
    val createdAt: String,
    val updatedAt: String,
    val isSeen: Boolean
)