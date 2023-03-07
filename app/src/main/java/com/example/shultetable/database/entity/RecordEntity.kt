package com.example.shultetable.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordEntity (
    @PrimaryKey
    val level: String,
    val millis: Long
)