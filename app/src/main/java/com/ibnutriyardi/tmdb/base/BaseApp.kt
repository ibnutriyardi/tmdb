package com.ibnutriyardi.tmdb.base

import android.app.Application
import com.ibnutriyardi.tmdb.di.localModule
import com.ibnutriyardi.tmdb.di.utilModule
import com.ibnutriyardi.tmdb.di.viewModelModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    private val appModules = listOf(localModule, utilModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        initHawk()
        initKoin()
    }

    private fun initHawk() {
        Hawk.init(this).build()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@BaseApp)
            modules(appModules)
        }
    }
}