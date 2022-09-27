package com.sandoval.mypokedex.domain.models.pokedex_detail

data class DPokedexDetailResponse(
    val abilities: List<DAbilites>?,
    val moves: List<DMoves>?,
    val name: String?,
    val types: List<DTypes>?,
)
