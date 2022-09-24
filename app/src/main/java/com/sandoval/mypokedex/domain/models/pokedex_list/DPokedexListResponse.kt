package com.sandoval.mypokedex.domain.models.pokedex_list

data class DPokedexListResponse(
    val count: Int?, val next: String?, val previous: String?, val results: List<DResult>?
)