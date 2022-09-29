package com.sandoval.mypokedex.domain.models.pokedex_detail

import com.sandoval.mypokedex.data.models.pokedex_detail.Sprites

data class DPokedexDetailResponse(
    val abilities: List<DAbilites>?,
    val moves: List<DMoves>?,
    val name: String?,
    val types: List<DTypes>?,
    val sprites: DSprites?
)
