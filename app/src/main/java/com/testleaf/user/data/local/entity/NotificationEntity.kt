package com.testleaf.user.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Md. Noweshed Akram on 1/30/2024.
 */
@Entity(
    tableName = "notification",
    indices = [Index(value = ["notificationId"], unique = true)]
)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val notificationId: Int,
    val title: String,
    val msg: String,
    val isSeen: Boolean,
    val createdAt: String,
    val updatedAt: String
)