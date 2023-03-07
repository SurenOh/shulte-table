package com.example.shultetable.repository

import com.example.shultetable.database.AppDatabase
import com.example.shultetable.mappers.RecordMapper
import com.example.shultetable.model.RecordModel

class RecordRepositoryImpl(db: AppDatabase, private val recordMapper: RecordMapper) : RecordRepository {
    private val recordDao = db.getRecordDao()
    override fun insert(record: RecordModel) {
        if (recordDao.getRecord(record.level).millis > record.millis) {
            recordDao.insert(recordMapper.mapToEntity(record))
        }
    }
}