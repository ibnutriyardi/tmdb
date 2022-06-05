package com.ibnutriyardi.tmdb.util

import com.orhanobut.hawk.Hawk
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LocalCache<T>(private val key: String, private val defaultValue: T) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return Hawk.get(key, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        Hawk.put(key, value)
    }

    fun delete(key: String) {
        Hawk.delete(key)
    }

    fun clear() {
        Hawk.deleteAll()
    }
}