package com.sandoval.mypokedex.data.models.pokedex_evolution

import com.sandoval.mypokedex.domain.models.pokedex_evolution.DEvolvesTo

data class EvolvesTo(
    val species: Species?
) {
    fun toDomainObject() = DEvolvesTo(
        species = species?.toDomainObject()
    )
}