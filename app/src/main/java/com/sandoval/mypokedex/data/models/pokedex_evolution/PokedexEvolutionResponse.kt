package com.sandoval.mypokedex.data.models.pokedex_evolution

import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse

data class PokedexEvolutionResponse(
    val chain: Chain?
) {
    fun toDomainObject() = DPokedexEvolutionResponse(
        chain = chain?.toDomainObject()
    )
}