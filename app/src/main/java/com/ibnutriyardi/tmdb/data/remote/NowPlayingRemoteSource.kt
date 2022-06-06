package com.ibnutriyardi.tmdb.data.remote

import com.ibnutriyardi.tmdb.model.ResponseContentList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NowPlayingRemoteSource {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page", encoded = true) page: Int = 1
    ): Response<ResponseContentList>

    @GET("tv/on_the_air")
    suspend fun getNowPlayingTV(
        @Query("page", encoded = true) page: Int = 1
    ): Response<ResponseContentList>
}