package com.jefisu.mobtape.service.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "Service")
data class ServiceDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "client")
    var client: String = "",

    @ColumnInfo(name = "cpf")
    var cpf: String = "",

    @ColumnInfo(name = "phone")
    var phone: String = "",

    @ColumnInfo(name = "type")
    var type: String = "",

    @ColumnInfo(name = "category")
    var category: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",
    )