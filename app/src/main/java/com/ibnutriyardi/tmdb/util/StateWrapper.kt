package com.ibnutriyardi.tmdb.util

class StateWrapper<out T>(private val content: T) {

    private var isHandled = false

    fun getEventIfNotHandled(): T? = if (isHandled) {
        null
    } else {
        isHandled = true
        content
    }
}