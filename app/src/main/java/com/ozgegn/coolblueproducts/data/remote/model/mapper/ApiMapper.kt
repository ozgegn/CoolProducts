package com.ozgegn.coolblueproducts.data.remote.model.mapper

interface ApiMapper<E, D> {
    fun mapToDomain(apiEntity: E?): D
}