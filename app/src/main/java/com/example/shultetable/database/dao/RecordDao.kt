package com.example.shultetable.database.dao

import androidx.room.*
import com.example.shultetable.database.entity.RecordEntity

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: RecordEntity)

    @Update
    fun update(record: RecordEntity)

    @Query("SELECT * FROM RecordEntity")
    fun getAllRecords() : List<RecordEntity>

    @Query("SELECT * FROM RecordEntity WHERE level = :level")
    fun getRecord(level: String) : List<RecordEntity>

}