package com.jefisu.mobtape.service.listener

interface ServiceListener {
    fun onClick(id: Int)
    fun onDelete(id: Int)
}