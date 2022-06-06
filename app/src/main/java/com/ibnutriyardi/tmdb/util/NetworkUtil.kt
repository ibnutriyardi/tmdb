package com.ibnutriyardi.tmdb.util

import com.ibnutriyardi.tmdb.BuildConfig
import com.ibnutriyardi.tmdb.data.local.UserLocalSource
import com.ibnutriyardi.tmdb.network.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

sealed class ResultCall<out T> {
    data class Success<T>(val data: T) : ResultCall<T>()
    data class Failed(val statusCode: String, val errorMessage: String) : ResultCall<Nothing>()
    data class Error(val exc: Exception, val errorMessage: String) : ResultCall<Nothing>()
}

fun createOkHttpClient(userLocalSource: UserLocalSource): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpBuilder = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)

    okHttpBuilder.addInterceptor(RequestInterceptor(userLocalSource))

    if (BuildConfig.DEBUG) okHttpBuilder.addInterceptor(loggingInterceptor)

    return okHttpBuilder.build()
}

inline fun <reified T> createApi(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}

suspend fun <T> awaitCall(block: suspend () -> Response<T>): ResultCall<T> {
    return try {
        val response = block()
        when {
            response.isSuccessful -> ResultCall.Success(response.body()!!)
            else -> ResultCall.Failed(response.code().toString(), "Internal server Error")
        }
    } catch (exception: Exception) {
        handleException(exception)
    }
}

private fun <T> handleException(exception: Exception): ResultCall<T> {
    return when (exception) {
        is SocketTimeoutException -> ResultCall.Error(exception, "Connection Timeout")
        is IOException -> ResultCall.Error(exception, "No Internet Connection")
        else -> ResultCall.Error(exception, "Internal Server Error")
    }
}

fun <A, B> ResultCall<A>.mapTo(block: (A) -> B): ResultCall<B> {
    return when (this) {
        is ResultCall.Success -> ResultCall.Success(block(this.data))
        is ResultCall.Failed -> ResultCall.Failed(this.statusCode, this.errorMessage)
        is ResultCall.Error -> ResultCall.Error(this.exc, this.errorMessage)
    }
}