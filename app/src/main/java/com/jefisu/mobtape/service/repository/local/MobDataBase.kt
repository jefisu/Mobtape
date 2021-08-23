package com.jefisu.mobtape.service.repository.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

// @Database(entities = [PriorityModel::class], version = 1)
abstract class MobDataBase(context: Context) : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: MobDataBase

        fun getDatabase(context: Context): MobDataBase {
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