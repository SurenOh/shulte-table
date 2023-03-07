package com.example.shultetable.utils

interface Mapper<Entity, Model> {
    fun mapFromEntity(entity: Entity): Model
    fun mapToEntity(model: Model): Entity

    fun mapListFromEntity(entityList: List<Entity>) = entityList.map { mapFromEntity(it) }
}