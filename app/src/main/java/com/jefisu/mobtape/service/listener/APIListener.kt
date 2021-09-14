package com.jefisu.mobtape.service.listener

import com.jefisu.mobtape.service.dto.UserDto

interface APIListener {
    fun onSucess(result: UserDto)
    fun onFailure(message: String)
}