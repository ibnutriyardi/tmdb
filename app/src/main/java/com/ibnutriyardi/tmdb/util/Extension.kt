package com.ibnutriyardi.tmdb.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.tabs.TabLayout
import kotlin.math.roundToInt

inline fun <T> LifecycleOwner.subscribeState(
    liveData: LiveData<StateWrapper<T>>,
    crossinline onEventUnhandled: (T) -> Unit
) {
    liveData.observe(this) {
        it?.getEventIfNotHandled()?.let(onEventUnhandled)
    }
}

inline fun <reified T : AppCompatActivity> Activity.startActivity(
    vararg params: Pair<String, *> = arrayOf(),
    actionIntent: Intent.() -> Unit = {}
) {
    val intent = IntentHelper.createIntent(this, T::class.java, params).apply(actionIntent)
    startActivity(intent)
}

fun TabLayout.onTabSelected(onSelected: (TabLayout.Tab) -> Unit) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onSelected.invoke(tab ?: TabLayout.Tab())
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }
    })
}

fun Context.convertDpToPx(dp: Int): Int {
    val displayMetrics: DisplayMetrics = resources.displayMetrics
    return dp.times(displayMetrics.xdpi.div(DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun AppCompatImageView.loadImage(url: String) {
    GlideApp.with(context).load(url).into(this)
}