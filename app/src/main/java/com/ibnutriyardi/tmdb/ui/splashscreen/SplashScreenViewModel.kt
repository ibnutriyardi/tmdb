package com.ibnutriyardi.tmdb.ui.splashscreen

import com.ibnutriyardi.tmdb.base.BaseViewModel
import com.ibnutriyardi.tmdb.data.local.UserLocalSource
import com.ibnutriyardi.tmdb.util.Constant
import com.ibnutriyardi.tmdb.util.RemoteConfigUtil
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val userLocalSource: UserLocalSource,
    private val remoteConfigUtil: RemoteConfigUtil
) : BaseViewModel<SplashScreenViewModel.Event, SplashScreenViewModel.State>() {

    sealed class Event {
        object FetchConfig : Event()
        object OnLoginClicked : Event()
    }

    sealed class State {
        object FetchComplete : State()
        object RedirectToHomepage : State()
    }

    override fun callEvent(event: Event) {
        when (event) {
            Event.FetchConfig -> checkUserToken()
            Event.OnLoginClicked -> openHomepage()
        }
    }

    private fun checkUserToken() = launch {
        if (userLocalSource.token.isBlank()) {
            remoteConfigUtil.updateData({
                updateUserData()
            }, {
                setState(State.FetchComplete)
            })
        } else {
            setState(State.FetchComplete)
        }
    }

    private fun updateUserData() = launch {
        userLocalSource.token = remoteConfigUtil.getString(Constant.RemoteConfigKey.AUTH_TOKEN)
        setState(State.FetchComplete)
    }

    private fun openHomepage() = launch {
        setState(State.RedirectToHomepage)
    }
}