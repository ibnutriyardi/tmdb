package com.ibnutriyardi.tmdb.domain

import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.network.repository.NowPlayingRepository
import com.ibnutriyardi.tmdb.util.ResultCall

interface GetNowPlayingMoviesUseCase {
    suspend fun execute(): ResultCall<List<ResponseContent>>
}

class GetNowPlayingMoviesUseCaseImpl(private val nowPlayingRepository: NowPlayingRepository) : GetNowPlayingMoviesUseCase {

    override suspend fun execute(): ResultCall<List<ResponseContent>> {
        return nowPlayingRepository.requestNowPlayingMovies()
    }
}