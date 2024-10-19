package com.testleaf.user.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.testleaf.user.data.local.entity.NotificationEntity
import com.testleaf.user.data.local.entity.TestEntity
import com.testleaf.user.data.local.entity.UserEntity
import com.testleaf.user.domain.usecase.EntityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Md. Noweshed Akram on 16/12/23.
 */
@HiltViewModel
class EntityViewModel @Inject constructor(
    private val app: Application,
    private val entityUseCase: EntityUseCase
) : AndroidViewModel(app) {

    val multiChoiceAns: MutableList<String> = mutableListOf()
    val singleChoiceAns: MutableState<String> = mutableStateOf("")

    fun insertUserData(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            entityUseCase.insertUserData(userEntity)
        }
    }

    fun getUserData(): Flow<UserEntity> {
        return entityUseCase.getUserData()
    }

    fun insertTestData(testEntity: TestEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            entityUseCase.insertTestData(testEntity)
        }
    }

    fun getCorrectMarks(marks: Int = 1): LiveData<Int> {
        return entityUseCase.getTestMark(marks)
    }

    fun getWrongMarks(marks: Int = 0): LiveData<Int> {
        return entityUseCase.getTestMark(marks)
    }

    fun clearLocalDb() {
        viewModelScope.launch(Dispatchers.IO) {
            entityUseCase.clearLocalDb()
        }
    }

    fun insertNotification(notificationEntity: NotificationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            entityUseCase.insertNotification(notificationEntity)
        }
    }

    fun getNotifications(): Flow<List<NotificationEntity>> {
        return entityUseCase.getNotifications()
    }

    fun updateNotificationReadStatus(id: Int, isSeen: Boolean): Int {
        return entityUseCase.updateNotificationReadStatus(id, isSeen)
    }

}