package com.testleaf.user.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.multidex.BuildConfig
import com.testleaf.user.R
import com.google.gson.Gson
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

fun getRemainingTimes(timeData: Long): String {
    return String.format(
        "%02d:%02d",
        (timeData / (1000 * 60) % 60),
        (timeData / 1000) % 60
    )
}

inline fun <reified T : Any> T.toPrettyJson(): String = Gson().toJson(this, T::class.java)

inline fun <reified T : Any> String.fromPrettyJson(): T = Gson().fromJson(this, T::class.java)

fun checkStringIsNull(value: String): String {
    return if (value != "null") {
        value
    } else {
        ""
    }
}

fun shareApp(context: Context) {
    val appPackageName = BuildConfig.APPLICATION_ID
    val appName = context.getString(R.string.app_name)
    val shareBodyText = "https://play.google.com/store/apps/details?id=$appPackageName"

    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TITLE, appName)
        putExtra(Intent.EXTRA_TEXT, shareBodyText)
    }
    context.startActivity(Intent.createChooser(sendIntent, null))
}

fun openContentLink(context: Context, url: String) {

    var newUrl = ""

    newUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "http://$url"
    } else {
        url
    }

    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newUrl))
    context.startActivity(browserIntent)
}