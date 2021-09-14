package com.jefisu.mobtape.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jefisu.mobtape.service.dto.ServiceDto

@Database(entities = [ServiceDto::class], version = 1)
abstract class MobDataBase: RoomDatabase() {

    abstract fun dao(): ServiceDAO

    companion object {
        private lateinit var INSTANCE: MobDataBase

        fun getDataBase(context: Context): MobDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(MobDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, MobDataBase::class.java, "mobDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}