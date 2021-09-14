package com.jefisu.mobtape.service.repository.local

import android.content.Context
import com.jefisu.mobtape.service.dto.ServiceDto

class ServiceRepository (context: Context) {

    private val mDataBase = MobDataBase.getDataBase(context).dao()

    //Salva um item na lista
    fun save (service: ServiceDto) : Boolean {
        return mDataBase.save(service) > 0
    }

    //Carrega um servico especifico
    fun get(id: Int): ServiceDto {
        return mDataBase.load(id)
    }

    //Carrega a lista
    fun loadAll () : List<ServiceDto> {
        return mDataBase.loadList()
    }

    //Atualiza um item da lista
    fun update(service: ServiceDto) : Boolean {
        return mDataBase.update(service) > 0
    }

    //Exclui um item da lista
    fun delete(service: ServiceDto) {
        return mDataBase.delete(service)
    }
}