package com.ibnutriyardi.tmdb.data.remote

import com.ibnutriyardi.tmdb.model.ResponseContentList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentRemoteSource {

    @GET("trending/{type}/week")
    suspend fun getTrending(
        @Path("type", encoded = true) type: String,
        @Query("page", encoded = true) id: Int = 1
    ): Response<ResponseContentList>

    @GET("discover/{type}")
    suspend fun getDiscover(
        @Path("type", encoded = true) type: String,
        @Query("page", encoded = true) id: Int = 1
    ): Response<ResponseContentList>
}