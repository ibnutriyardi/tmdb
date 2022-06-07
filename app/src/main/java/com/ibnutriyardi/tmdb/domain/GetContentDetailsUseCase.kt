package com.ibnutriyardi.tmdb.domain

import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.network.repository.ContentRepository
import com.ibnutriyardi.tmdb.util.ResultCall

interface GetContentDetailsUseCase {
    suspend fun execute(type: String, id: Int): ResultCall<ResponseContent>
}

class GetContentDetailsUseCaseImpl(private val contentRepository: ContentRepository) : GetContentDetailsUseCase {

    override suspend fun execute(type: String, id: Int): ResultCall<ResponseContent> {
        return contentRepository.requestContentDetails(type, id)
    }
}