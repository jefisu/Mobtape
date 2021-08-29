package com.jefisu.mobtape.service.repository.local

import android.content.Context
import com.jefisu.mobtape.service.model.ServiceModel

class ServiceRepository (context: Context) {

    private val mDataBase = MobDataBase.getDataBase(context).dao()

    //Salva um item na lista
    fun save (service: ServiceModel) : Boolean {
        return mDataBase.save(service) > 0
    }

    //Carrega um servico especifico
    fun get(id: Int): ServiceModel {
        return mDataBase.load(id)
    }

    //Carrega a lista
    fun loadAll () : List<ServiceModel> {
        return mDataBase.loadList()
    }

    //Atualiza um item da lista
    fun update(service: ServiceModel) : Boolean {
        return mDataBase.update(service) > 0
    }

    //Exclui um item da lista
    fun delete(service: ServiceModel) {
        return mDataBase.delete(service)
    }
}