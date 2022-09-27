package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DTypes

data class Types(
    val type: Type?
) {
    fun toDomainObject() = DTypes(
        type = type?.toDomainObject()
    )
}