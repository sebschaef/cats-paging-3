package de.sebschaef.cat.network

import de.sebschaef.cat.model.constant.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-api-key", Constants.API_KEY)
            .build()

        return chain.proceed(request)
    }

}