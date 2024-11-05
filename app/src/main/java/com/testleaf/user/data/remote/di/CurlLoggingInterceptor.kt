package com.testleaf.user.data.remote.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Noweshed on 4/11/24.
 */
class CurlLoggingInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val curlCommand = buildCurlCommand(request)
        Log.d("okhttp curl", curlCommand)
        return chain.proceed(request)
    }

    private fun buildCurlCommand(request: Request): String {
        val curlCommand = StringBuilder("curl -X ${request.method} \"${request.url}\"")

        for (header in request.headers) {
            curlCommand.append(" -H \"${header.first}: ${header.second}\"")
        }

        request.body?.let { body ->
            val buffer = okio.Buffer()
            body.writeTo(buffer)
            curlCommand.append(" -d '${buffer.readUtf8()}'")
        }
        return curlCommand.toString()
    }
}