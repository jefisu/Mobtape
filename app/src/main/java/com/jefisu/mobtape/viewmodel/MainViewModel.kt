package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.constants.MobConstants.Companion.SHARED
import com.jefisu.mobtape.service.repository.local.SharedPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mSharedPreferences = SharedPreferences(application)

    private val mLogout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = mLogout

    fun logout() {
        mSharedPreferences.remove(SHARED.TOKEN_KEY)
        mSharedPreferences.remove(SHARED.USER_KEY)
        mSharedPreferences.remove(SHARED.USER_NAME)

        mLogout.value = true
    }
}