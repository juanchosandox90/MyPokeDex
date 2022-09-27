package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DType

data class Type(
    val name: String?,
    val url: String?
){
    fun toDomainObject() = DType(
        name = name ?: "",
        url = url ?: ""
    )
}