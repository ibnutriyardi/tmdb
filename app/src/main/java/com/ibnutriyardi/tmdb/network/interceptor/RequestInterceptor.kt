package com.ibnutriyardi.tmdb.network.interceptor

import com.ibnutriyardi.tmdb.BuildConfig
import com.ibnutriyardi.tmdb.data.local.UserLocalSource
import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor(private val userLocalSource: UserLocalSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = if (userLocalSource.token.isBlank()) {
            val originalHttpUrl = original.url
            val httpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.GUEST_API_KEY)
                .build()

            original.newBuilder().url(httpUrl).build()
        } else {
            original.newBuilder().addHeader("Authorization", "Bearer ${userLocalSource.token}").build()
        }

        return chain.proceed(requestBuilder)
    }
}