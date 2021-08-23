package com.jefisu.mobtape.service.listener

import com.jefisu.mobtape.service.model.HeaderModel

interface APIListener {

    fun onSucess(model: HeaderModel)

    fun onFailure(str: String)

}