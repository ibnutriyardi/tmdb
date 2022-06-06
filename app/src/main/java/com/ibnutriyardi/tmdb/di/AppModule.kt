package com.ibnutriyardi.tmdb.di

import com.ibnutriyardi.tmdb.data.local.UserLocalSource
import com.ibnutriyardi.tmdb.data.remote.ContentRemoteSource
import com.ibnutriyardi.tmdb.data.remote.NowPlayingRemoteSource
import com.ibnutriyardi.tmdb.domain.*
import com.ibnutriyardi.tmdb.network.repository.ContentRepository
import com.ibnutriyardi.tmdb.network.repository.ContentRepositoryImpl
import com.ibnutriyardi.tmdb.network.repository.NowPlayingRepository
import com.ibnutriyardi.tmdb.network.repository.NowPlayingRepositoryImpl
import com.ibnutriyardi.tmdb.ui.home.MoviesViewModel
import com.ibnutriyardi.tmdb.ui.splashscreen.SplashScreenViewModel
import com.ibnutriyardi.tmdb.util.RemoteConfigUtil
import com.ibnutriyardi.tmdb.util.createApi
import com.ibnutriyardi.tmdb.util.createOkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val localModule = module {
    single { UserLocalSource() }
}

val utilModule = module {
    single { RemoteConfigUtil() }
}

val networkModule = module {
    single { createOkHttpClient(get()) }
    single { createApi<NowPlayingRemoteSource>(get()) }
    single { createApi<ContentRemoteSource>(get()) }
}

val repositoryModule = module {
    single<NowPlayingRepository> { NowPlayingRepositoryImpl(get()) }
    single<ContentRepository> { ContentRepositoryImpl(get()) }
}

val domainModule = module {
    single<GetNowPlayingMoviesUseCase> { GetNowPlayingMoviesUseCaseImpl(get()) }
    single<GetTrendingUseCase> { GetTrendingUseCaseImpl(get()) }
    single<GetDiscoverUseCase> { GetDiscoverUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get(), get()) }
    viewModel { MoviesViewModel(get(), get(), get()) }
}
