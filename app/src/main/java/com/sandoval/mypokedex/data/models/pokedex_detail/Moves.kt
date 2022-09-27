package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DMoves

data class Moves(
    val move: Move?
){
    fun toDomainObject () = DMoves(
        move = move?.toDomainObject()
    )
}