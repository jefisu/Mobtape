package com.jefisu.mobtape.service.model

data class ServiceModel(
    var id: Int?,
    var client: String = "",
    var cpf: String = "",
    var phone: String = "",
    var type: String = "",
    var category: String = ""
)