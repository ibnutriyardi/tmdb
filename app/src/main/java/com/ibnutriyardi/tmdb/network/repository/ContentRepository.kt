package com.ibnutriyardi.tmdb.network.repository

import com.ibnutriyardi.tmdb.data.remote.ContentRemoteSource
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.ResultCall
import com.ibnutriyardi.tmdb.util.awaitCall
import com.ibnutriyardi.tmdb.util.mapTo

interface ContentRepository {
    suspend fun requestTrending(type: String, page: Int = 1): ResultCall<List<ResponseContent>>
    suspend fun requestDiscover(type: String, page: Int = 1): ResultCall<List<ResponseContent>>
    suspend fun requestContentDetails(type: String, id: Int): ResultCall<ResponseContent>
}

class ContentRepositoryImpl(private val contentRemoteSource: ContentRemoteSource) : ContentRepository {

    override suspend fun requestTrending(type: String, page: Int): ResultCall<List<ResponseContent>> {
        return awaitCall { contentRemoteSource.getTrending(type, page) }.mapTo {
            it.data
        }
    }

    override suspend fun requestDiscover(type: String, page: Int): ResultCall<List<ResponseContent>> {
        return awaitCall { contentRemoteSource.getDiscover(type, page) }.mapTo {
            it.data
        }
    }

    override suspend fun requestContentDetails(type: String, id: Int): ResultCall<ResponseContent> {
        return awaitCall { contentRemoteSource.getContentDetails(type, id) }
    }
}