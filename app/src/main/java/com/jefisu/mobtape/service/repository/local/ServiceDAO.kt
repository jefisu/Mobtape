package com.jefisu.mobtape.service.repository.local

import androidx.room.*
import com.jefisu.mobtape.service.model.ServiceModel

@Dao
interface ServiceDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(service: ServiceModel): Long

    @Query("SELECT * FROM Service WHERE id = :id")
    fun load(id: Int): ServiceModel

    @Query("SELECT * FROM Service ORDER BY id ASC")
    fun loadList(): List<ServiceModel>

    @Update()
    fun update(service: ServiceModel): Int

    @Delete
    fun delete(service: ServiceModel)
}