package com.ibnutriyardi.tmdb.ui.splashscreen

import android.annotation.SuppressLint
import com.ibnutriyardi.tmdb.R
import com.ibnutriyardi.tmdb.base.BaseActivity
import com.ibnutriyardi.tmdb.ui.home.HomeActivity
import com.ibnutriyardi.tmdb.util.startActivity
import com.ibnutriyardi.tmdb.util.subscribeState
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivity() {

    private val splashScreenViewModel by viewModel<SplashScreenViewModel>()

    override fun getLayoutResource(): Int = R.layout.activity_splash_screen

    override fun initPage() {
        txt_guest_login?.setOnClickListener {
            splashScreenViewModel.callEvent(SplashScreenViewModel.Event.OnLoginClicked)
        }

        splashScreenViewModel.callEvent(SplashScreenViewModel.Event.FetchConfig)
    }

    override fun observeData() {
        subscribeState(splashScreenViewModel.state) {
            when (it) {
                SplashScreenViewModel.State.FetchComplete -> txt_guest_login?.isEnabled = true
                SplashScreenViewModel.State.RedirectToHomepage -> {
                    startActivity<HomeActivity>()
                    finish()
                }
            }
        }
    }
}