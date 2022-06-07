package com.ibnutriyardi.tmdb.ui.content

import com.ibnutriyardi.tmdb.base.BaseViewModel
import com.ibnutriyardi.tmdb.domain.GetContentDetailsUseCase
import com.ibnutriyardi.tmdb.model.ResponseContent
import com.ibnutriyardi.tmdb.util.ResultCall
import kotlinx.coroutines.launch

class ContentViewModel(
    private val getContentDetailsUseCase: GetContentDetailsUseCase
) : BaseViewModel<ContentViewModel.Event, ContentViewModel.State>() {

    sealed class Event {
        data class LoadDetails(val type: String, val id: Int) : Event()
    }

    sealed class State {
        data class ShowDetails(val content: ResponseContent) : State()
    }

    override fun callEvent(event: Event) {
        when (event) {
            is Event.LoadDetails -> requestContentDetails(event.type, event.id)
        }
    }

    private fun requestContentDetails(type: String, id: Int) = launch {
        when (val result = getContentDetailsUseCase.execute(type, id)) {
            is ResultCall.Success -> setState(State.ShowDetails(result.data))
            else -> {

            }
        }
    }
}