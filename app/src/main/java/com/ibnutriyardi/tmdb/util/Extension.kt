package com.ibnutriyardi.tmdb.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LifecycleOwner.subscribeState(
    liveData: LiveData<StateWrapper<T>>,
    crossinline onEventUnhandled: (T) -> Unit
) {
    liveData.observe(this) {
        it?.getEventIfNotHandled()?.let(onEventUnhandled)
    }
}

inline fun <reified T : AppCompatActivity> Activity.startActivity(
    vararg params: Pair<String, *>,
    actionIntent: Intent.() -> Unit
) {
    val intent = IntentHelper.createIntent(this, T::class.java, params).apply(actionIntent)
    startActivity(intent)
}