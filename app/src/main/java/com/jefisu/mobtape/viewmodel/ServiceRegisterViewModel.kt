package com.jefisu.mobtape.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jefisu.mobtape.service.dto.ServiceDto
import com.jefisu.mobtape.service.repository.local.ServiceRepository

class ServiceRegisterViewModel(application: Application) : AndroidViewModel(application) {

    // Acesso a dados
    private var mRepository = ServiceRepository(application.applicationContext)

    private val mSaveService = MutableLiveData<Boolean>()
    val saveService: LiveData<Boolean> = mSaveService

    /**
     * Salva o serviço
     * */
    fun save(service: ServiceDto) {
        mSaveService.value = mRepository.save(service)
    }
}