package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.constants.MobConstants.Companion.SHARED
import com.jefisu.mobtape.service.listener.APIListener
import com.jefisu.mobtape.service.listener.ValidationListener
import com.jefisu.mobtape.service.model.HeaderModel
import com.jefisu.mobtape.service.repository.UserRepository
import com.jefisu.mobtape.service.repository.local.SharedPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository = UserRepository(application)
    private val mSharedPreferences = SharedPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    /**
     * Faz login usando API
     */
    fun doLogin (email: String, password: String) {
        mRepository.login(email, password, object : APIListener {

            override fun onSucess(model: HeaderModel) {
                mSharedPreferences.save(SHARED.TOKEN_KEY, model.token)
                mSharedPreferences.save(SHARED.USER_KEY, model.token)
                mSharedPreferences.save(SHARED.USER_NAME, model.token)

                mLogin.value = ValidationListener()

            }

            override fun onFailure(str: String) {
                mLogin.value = ValidationListener(str)
            }

        })
    }
}