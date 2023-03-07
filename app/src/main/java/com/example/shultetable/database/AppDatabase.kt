package com.example.shultetable.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shultetable.database.dao.RecordDao
import com.example.shultetable.database.entity.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecordDao(): RecordDao

    companion object {
        const val DB_NAME = "myDatabase"
    }
}