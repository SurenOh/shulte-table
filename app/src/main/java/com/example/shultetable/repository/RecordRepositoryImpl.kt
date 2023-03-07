package com.example.shultetable.repository

import com.example.shultetable.database.AppDatabase
import com.example.shultetable.mappers.RecordMapper
import com.example.shultetable.model.RecordModel

class RecordRepositoryImpl(db: AppDatabase, private val recordMapper: RecordMapper) : RecordRepository {
    private val recordDao = db.getRecordDao()

    override fun insert(record: RecordModel) {
        val dbInfo = recordDao.getRecord(record.level)
        if (dbInfo.isNotEmpty()) {
            if (dbInfo.first().millis > record.millis) {
                recordDao.insert(recordMapper.mapToEntity(record))
            }
        } else {
            recordDao.insert(recordMapper.mapToEntity(record))
        }
    }

    override fun getAllRecords() = recordMapper.mapListFromEntity(recordDao.getAllRecords())

}