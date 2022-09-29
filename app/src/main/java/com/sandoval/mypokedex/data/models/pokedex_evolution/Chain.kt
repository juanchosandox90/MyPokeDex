package com.sandoval.mypokedex.data.models.pokedex_evolution

import com.sandoval.mypokedex.domain.models.pokedex_evolution.DChain

data class Chain(
    val evolves_to: List<EvolvesTo>?
) {
    fun toDomainObject() = DChain(
        evolves_to = evolves_to?.map { it.toDomainObject() } ?: emptyList()
    )
}