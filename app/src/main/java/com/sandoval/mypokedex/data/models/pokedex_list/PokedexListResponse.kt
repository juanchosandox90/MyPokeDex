package com.sandoval.mypokedex.data.models.pokedex_list

data class PokedexListResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Result>?
)