package com.testleaf.user.ui.component

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.testleaf.user.ui.theme.ErrorColor
import com.testleaf.user.ui.theme.TextColor
import com.testleaf.user.ui.theme.Typography
import com.testleaf.user.utils.getRemainingTimes

/**
 * Created by noweshedakram on 27/7/23.
 */
@Composable
fun CountDownTimer(
    timeInMillisecond: Long,
    onTimesUp: () -> Unit
) {

    val millisInFuture: Long = timeInMillisecond

    val timeData = rememberSaveable {
        mutableLongStateOf(millisInFuture)
    }

    var showTimer by rememberSaveable { mutableStateOf(false) }

    val countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            Log.d("CountDownTimer", "onTick: $millisUntilFinished")

            timeData.longValue = millisUntilFinished
        }

        override fun onFinish() {
            showTimer = false
            onTimesUp()
        }
    }

    DisposableEffect(key1 = showTimer) {
        showTimer = false
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }

    Text(
        text = if (timeData.longValue > 1000) {
            getRemainingTimes(timeData.longValue)
        } else "Time's Up",
        color = if (timeData.longValue > 300000) {
            TextColor
        } else {
            ErrorColor
        },
        style = Typography.titleMedium
    )

}