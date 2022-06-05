package com.ibnutriyardi.tmdb.di

import com.ibnutriyardi.tmdb.data.local.UserLocalSource
import com.ibnutriyardi.tmdb.ui.splashscreen.SplashScreenViewModel
import com.ibnutriyardi.tmdb.util.RemoteConfigUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val localModule = module {
    single { UserLocalSource() }
}

val utilModule = module {
    single { RemoteConfigUtil() }
}

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get(), get()) }
}
