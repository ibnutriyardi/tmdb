package com.ibnutriyardi.tmdb.domain

import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.network.repository.ContentRepository
import com.ibnutriyardi.tmdb.util.ResultCall

interface GetTrendingUseCase {
    suspend fun execute(type: String, page: Int = 1): ResultCall<List<ResponseContent>>
}

class GetTrendingUseCaseImpl(private val contentRepository: ContentRepository) : GetTrendingUseCase {

    override suspend fun execute(type: String, page: Int): ResultCall<List<ResponseContent>> {
        return contentRepository.requestTrending(type, page)
    }
}