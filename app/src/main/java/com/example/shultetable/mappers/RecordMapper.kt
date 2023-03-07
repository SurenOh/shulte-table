package com.example.shultetable.mappers

import com.example.shultetable.database.entity.RecordEntity
import com.example.shultetable.model.RecordModel
import com.example.shultetable.utils.Mapper

class RecordMapper : Mapper<RecordEntity, RecordModel> {

    override fun mapFromEntity(entity: RecordEntity) = RecordModel(
        level = entity.level,
        millis = entity.millis
    )

    override fun mapToEntity(model: RecordModel) = RecordEntity(
        level = model.level,
        millis = model.millis
    )

}