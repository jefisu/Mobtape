package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.model.ServiceModel
import com.jefisu.mobtape.service.repository.local.ServiceRepository

class UpdateViewModel(application: Application) : AndroidViewModel(application) {

    // Acesso a dados
    private var mRepository = ServiceRepository(application.applicationContext)

    private val mService = MutableLiveData<ServiceModel>()
    val service: LiveData<ServiceModel> = mService

    private val mUpdateService = MutableLiveData<Boolean>()
    val updateService: LiveData<Boolean> = mUpdateService

    /**
     * Carrega o serviço para fazer o atualização dos dados
     */
    fun load(id: Int) {
        mService.value = mRepository.get(id)
    }

    /**
     * Atualiza o serviço
     */
    fun update(service: ServiceModel) {
        mUpdateService.value = mRepository.update(service)
    }
}