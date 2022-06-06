package com.ibnutriyardi.tmdb.ui.home

import com.ibnutriyardi.tmdb.base.BaseViewModel

class TVShowsViewModel : BaseViewModel<TVShowsViewModel.Event, TVShowsViewModel.State>() {

    sealed class Event

    sealed class State

    override fun callEvent(event: Event) {

    }
}