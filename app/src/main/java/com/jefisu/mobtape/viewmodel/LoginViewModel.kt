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

    // Acesso a dados
    private val mUserRepository = UserRepository(application)
    private val mSharedPreferences = SharedPreferences(application)

    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin

    private val mLoggedUser = MutableLiveData<Boolean>()
    val loggedUser: LiveData<Boolean> = mLoggedUser

    /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mUserRepository.login(email, password, object : APIListener {
            override fun onSucess(result: HeaderModel) {
                mSharedPreferences.save(SHARED.TOKEN_KEY, result.token)
                mSharedPreferences.save(SHARED.USER_KEY, result.userKey)
                mSharedPreferences.save(SHARED.USER_NAME, result.name)

                mLogin.value = ValidationListener()
            }

            override fun onFailure(message: String) {
                mLogin.value = ValidationListener(message)
            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {
        val tokenKey = mSharedPreferences.get(SHARED.TOKEN_KEY)
        val userKey = mSharedPreferences.get(SHARED.USER_KEY)

        // Se token e user key forem diferentes de vazio, usuário está logado
        val logged = (tokenKey != "" && userKey != "")

        // Atualiza o valor
        mLoggedUser.value = logged
    }
}