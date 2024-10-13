package com.testleaf.user.utils

import android.util.Patterns
import com.testleaf.user.utils.AppConstant.BASE_URL
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Noweshed on 28/9/24.
 */
class HostUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        val requestUrl = original.url.toString()
        val protocol = "(?i:http|https|rtsp)://"
        var newURL = requestUrl.replaceFirst(protocol.toRegex(), "")
            .replaceFirst(Patterns.DOMAIN_NAME.toString().toRegex(), "")
        newURL = if (validateBackSlash(newURL)) BASE_URL + newURL else newURL.replaceFirst(
            "/".toRegex(),
            BASE_URL
        )
        original = original.newBuilder()
            .url(newURL)
            .build()

        return chain.proceed(original)
    }

    private fun validateBackSlash(str: String): Boolean {
        return !str.endsWith("/")
    }

}