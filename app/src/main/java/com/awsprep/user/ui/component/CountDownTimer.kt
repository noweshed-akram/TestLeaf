package com.awsprep.user.ui.component

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

/**
 * Created by noweshedakram on 27/7/23.
 */
@Composable
fun CountDownTimer(
    onResendClick: () -> Unit
) {

    var millisInFuture: Long = 61000

    val timeData = rememberSaveable {
        mutableLongStateOf(millisInFuture)
    }

    var showTimer by rememberSaveable { mutableStateOf(false) }

    val countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            Log.d("TAG", "onTick: $millisUntilFinished")

            timeData.value = millisUntilFinished
        }

        override fun onFinish() {
            showTimer = false
        }
    }

    DisposableEffect(key1 = showTimer) {
        showTimer = false
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }

    TextButton(onClick = {
        showTimer = true
        millisInFuture = 61000
        onResendClick()
    }) {
        Text(
            text = if (timeData.value > 1000) String.format(
                "%02d:%02d",
                (timeData.value / (1000 * 60) % 60),
                (timeData.value / 1000) % 60
            ) else "Re-Send"
        )
    }

}