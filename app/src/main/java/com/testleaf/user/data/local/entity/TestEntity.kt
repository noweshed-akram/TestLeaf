package com.testleaf.user.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Md. Noweshed Akram on 15/12/23.
 */
@Entity(
    tableName = "TestEntity",
    indices = [Index(value = ["quesId"], unique = true)]
)
data class TestEntity(
    @PrimaryKey
    val quesId: String,
    val correctAnswers: String,
    val selectedAnswers: String,
    val marks: Int
)