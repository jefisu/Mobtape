package com.jefisu.mobtape.service.repository.local

import androidx.room.*
import com.jefisu.mobtape.service.dto.ServiceDto

@Dao
interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(service: ServiceDto): Long

    @Query("SELECT * FROM Service WHERE id = :id")
    fun load(id: Int): ServiceDto

    @Query("SELECT * FROM Service ORDER BY id ASC")
    fun loadList(): List<ServiceDto>

    @Update()
    fun update(service: ServiceDto): Int

    @Delete
    fun delete(service: ServiceDto)
}