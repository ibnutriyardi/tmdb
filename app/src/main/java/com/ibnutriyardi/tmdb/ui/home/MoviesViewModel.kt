package com.ibnutriyardi.tmdb.ui.home

import com.ibnutriyardi.tmdb.base.BaseViewModel
import com.ibnutriyardi.tmdb.domain.GetDiscoverUseCase
import com.ibnutriyardi.tmdb.domain.GetNowPlayingMoviesUseCase
import com.ibnutriyardi.tmdb.domain.GetTrendingUseCase
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.Constant
import com.ibnutriyardi.tmdb.util.ResultCall
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getDiscoverUseCase: GetDiscoverUseCase
) : BaseViewModel<MoviesViewModel.Event, MoviesViewModel.State>() {

    companion object {
        private const val TITLE_TRENDING = "Trending"
        private const val TITLE_DISCOVER = "Discover"
    }

    private var _nowPlayingMovies: List<ResponseContent> = listOf()
    private var _section: MutableList<Pair<String, List<ResponseContent>>> = mutableListOf()

    sealed class Event {
        object LoadContent : Event()
    }

    sealed class State {
        data class ShowBanner(val nowPlayingMovies: List<ResponseContent>) : State()
        data class ShowSection(val sections: List<Pair<String, List<ResponseContent>>>) : State()
        object ShowError : State()
        object HideBanner : State()
    }

    override fun callEvent(event: Event) {
        when (event) {
            Event.LoadContent -> loadContent()
        }
    }

    private fun loadContent() = launch {
        val deferred = mutableListOf<Deferred<Any>>()
        deferred.apply {
            add(requestNowPlayingMoviesAsync())
            add(requestTrendingMoviesAsync())
            add(requestDiscoverAsync())
        }

        deferred.awaitAll()
        if (_nowPlayingMovies.isEmpty() && _section.isEmpty()) {

            return@launch
        }

        setState(State.ShowBanner(_nowPlayingMovies))
        setState(State.ShowSection(_section))
    }

    private fun requestNowPlayingMoviesAsync() = async {
        when (val result = getNowPlayingMoviesUseCase.execute()) {
            is ResultCall.Success -> _nowPlayingMovies = result.data.subList(0, 5)
            else -> setState(State.HideBanner)
        }
    }

    private fun requestTrendingMoviesAsync() = async {
        val result = getTrendingUseCase.execute(Constant.ContentType.MOVIE)
        if (result is ResultCall.Success) _section.add(0, Pair(TITLE_TRENDING, result.data))
    }

    private fun requestDiscoverAsync() = async {
        val result = getDiscoverUseCase.execute(Constant.ContentType.MOVIE)
        if (result is ResultCall.Success) if (_section.isEmpty()) {
            _section.add(Pair(TITLE_DISCOVER, result.data))
        } else {
            _section.add(1, Pair(TITLE_DISCOVER, result.data))
        }
    }
}