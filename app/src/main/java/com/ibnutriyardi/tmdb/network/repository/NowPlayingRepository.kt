package com.ibnutriyardi.tmdb.network.repository

import com.ibnutriyardi.tmdb.data.remote.NowPlayingRemoteSource
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.ResultCall
import com.ibnutriyardi.tmdb.util.awaitCall
import com.ibnutriyardi.tmdb.util.mapTo

interface NowPlayingRepository {
    suspend fun requestNowPlayingMovies(page: Int = 1): ResultCall<List<ResponseContent>>
    suspend fun requestNowPlayingTV(page: Int = 1): ResultCall<List<ResponseContent>>
}

class NowPlayingRepositoryImpl(private val nowPlayingRemoteSource: NowPlayingRemoteSource) : NowPlayingRepository {

    override suspend fun requestNowPlayingMovies(page: Int): ResultCall<List<ResponseContent>> {
        return awaitCall { nowPlayingRemoteSource.getNowPlayingMovies(page) }.mapTo {
            it.data
        }
    }

    override suspend fun requestNowPlayingTV(page: Int): ResultCall<List<ResponseContent>> {
        return awaitCall { nowPlayingRemoteSource.getNowPlayingTV(page) }.mapTo {
            it.data
        }
    }
}