package com.ibnutriyardi.tmdb.ui.splashscreen

import android.annotation.SuppressLint
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.base.BaseActivity
import com.ibnutriyardi.tmdb.util.subscribeState
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {

    private val splashScreenViewModel by viewModel<SplashScreenViewModel>()

    override fun getLayoutResource(): Int = R.layout.activity_splash_screen

    override fun initPage() {
        splashScreenViewModel.callEvent(SplashScreenViewModel.Event.FetchConfig)
    }

    override fun observeData() {
        subscribeState(splashScreenViewModel.state) {
            when (it) {
                SplashScreenViewModel.State.RedirectToHomepage -> {
                    // TODO : Redirect to homepage
                }
            }
        }
    }
}