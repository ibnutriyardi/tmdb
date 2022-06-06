package com.ibnutriyardi.tmdb.domain

import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.network.repository.ContentRepository
import com.ibnutriyardi.tmdb.util.ResultCall

interface GetDiscoverUseCase {
    suspend fun execute(type: String, page: Int = 1): ResultCall<List<ResponseContent>>
}

class GetDiscoverUseCaseImpl(private val contentRepository: ContentRepository) : GetDiscoverUseCase {

    override suspend fun execute(type: String, page: Int): ResultCall<List<ResponseContent>> {
        return contentRepository.requestDiscover(type, page)
    }
}