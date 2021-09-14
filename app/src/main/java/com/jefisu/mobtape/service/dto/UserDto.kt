package com.jefisu.mobtape.service.dto

import com.google.gson.annotations.SerializedName

class UserDto {

    @SerializedName("token")
    lateinit var token: String

    @SerializedName("personKey")
    lateinit var userKey: String

    @SerializedName("name")
    lateinit var name: String
}