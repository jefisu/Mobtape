package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.constants.MobConstants
import com.jefisu.mobtape.service.listener.APIListener
import com.jefisu.mobtape.service.listener.ValidationListener
import com.jefisu.mobtape.service.model.HeaderModel
import com.jefisu.mobtape.service.repository.UserRepository
import com.jefisu.mobtape.service.repository.local.SharedPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    // Acesso a dados
    private val mUserRepository = UserRepository(application)
    private val mSharedPreferences = SharedPreferences(application)

    private val mCreate = MutableLiveData<ValidationListener>()
    val create: LiveData<ValidationListener> = mCreate

    /**
     * Salva um novo usuário
     * */
    fun insertUser(name: String, email: String, password: String) {
        mUserRepository.create(name, email, password, object : APIListener {
            override fun onSucess(result: HeaderModel) {
                mSharedPreferences.save(MobConstants.Companion.SHARED.TOKEN_KEY, result.token)
                mSharedPreferences.save(MobConstants.Companion.SHARED.USER_KEY, result.userKey)
                mSharedPreferences.save(MobConstants.Companion.SHARED.USER_NAME, result.name)

                mCreate.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mCreate.value = ValidationListener(message)
            }

        })
    }
}