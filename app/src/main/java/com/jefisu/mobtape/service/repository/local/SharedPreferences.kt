package com.jefisu.mobtape.service.repository.local

import android.content.Context
import android.content.SharedPreferences


class SharedPreferences(context: Context) {

    private val mPreferences: SharedPreferences =
        context.getSharedPreferences("mobShared", Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        mPreferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }

    fun get(key: String): String {
        return mPreferences.getString(key, "") ?: ""
    }
}