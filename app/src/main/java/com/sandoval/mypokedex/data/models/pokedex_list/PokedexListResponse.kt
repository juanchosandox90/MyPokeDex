package com.sandoval.mypokedex.data.models.pokedex_list

import com.sandoval.mypokedex.domain.models.pokedex_list.DPokedexListResponse

data class PokedexListResponse(
    val count: Int?, val next: String?, val previous: String?, val results: List<Result>?
) {
    fun toDomainObject() = DPokedexListResponse(
        count = count ?: 0,
        next ?: "",
        previous ?: "",
        results = results?.map { it.toDomainObject() })
}