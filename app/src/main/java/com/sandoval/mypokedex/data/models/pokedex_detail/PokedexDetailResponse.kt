package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse

data class PokedexDetailResponse(
    val abilities: List<Abilities>?,
    val moves: List<Moves>?,
    val name: String?,
    val types: List<Types>?,
    val sprites: Sprites?
) {
    fun toDomainObject() = DPokedexDetailResponse(
        abilities = abilities?.map { it.toDomainObject() } ?: emptyList(),
        moves = moves?.map { it.toDomainObject() } ?: emptyList(),
        name = name ?: "",
        types = types?.map { it.toDomainObject() } ?: emptyList(),
        sprites = sprites?.toDomainObject()
    )
}
