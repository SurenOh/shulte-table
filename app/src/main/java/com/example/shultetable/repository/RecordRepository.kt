package com.example.shultetable.repository

import com.example.shultetable.model.RecordModel

interface RecordRepository {
    fun insert(record: RecordModel)
    fun getAllRecords() : List<RecordModel>
}