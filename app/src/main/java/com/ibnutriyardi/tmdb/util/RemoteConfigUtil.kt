package com.ibnutriyardi.tmdb.util

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class RemoteConfigUtil {

    companion object {
        private const val DEBUG_INTERVAL = 60L
        private const val INTERVAL = 3600L
    }

    private val configSettings by lazy {
        remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                DEBUG_INTERVAL
            } else {
                INTERVAL
            }
        }
    }

    private val remoteConfig by lazy {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
        }
    }

    fun updateData(onSuccess: () -> Unit, onFailed : () -> Unit = {}) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Firebase RemoteConfig", "Activated")
                onSuccess.invoke()
            } else {
                Log.d("Firebase RemoteConfig", it.exception?.message ?: "Unknown Error")
                onFailed.invoke()
            }
        }
    }

    fun getString(key: String): String = remoteConfig.getString(key)
}