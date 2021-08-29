package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.constants.MobConstants.Companion.SHARED
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.service.repository.local.ServiceRepository
import com.jefisu.mobtape.service.repository.local.SharedPreferences
import com.jefisu.mobtape.view.adapter.ServiceAdapter

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mSharedPreferences = SharedPreferences(application)
    private var mRepository = ServiceRepository(application)

    private val mLogout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = mLogout

    /**
     * Faz logout do usuário
     */
    fun logout() {
        mSharedPreferences.remove(SHARED.TOKEN_KEY)
        mSharedPreferences.remove(SHARED.USER_KEY)
        mSharedPreferences.remove(SHARED.USER_NAME)

        mLogout.value = true
    }

    /**
     * Faz atualização da lista
     */
    fun getUpdateList(): List<ServiceModel> = mRepository.loadAll()

    /**
     * Faz a remoção de um serviço
     */
    fun delete(id: Int) {
        val service = mRepository.get(id)
        mRepository.delete(service)
    }
}