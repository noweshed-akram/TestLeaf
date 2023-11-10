package com.awsprep.user.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by noweshedakram on 14/7/23.
 */
fun Context.displayToast(massage: String) {
    Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun checkStringIsNull(value: String): String {
    return if (value != "null") {
        value
    } else {
        ""
    }
}