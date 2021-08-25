package com.jefisu.mobtape.service.listener

import com.jefisu.mobtape.service.model.HeaderModel

interface APIListener {
    fun onSucess(result: HeaderModel)
    fun onFailure(message: String)
}