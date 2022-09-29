package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DMove

data class Move(
    val name: String?,
    val url: String?
){
    fun toDomainObject() = DMove(
        name = name ?: "",
        url = url ?: ""
    )
}