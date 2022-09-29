package com.sandoval.mypokedex.data.models.pokedex_evolution

import com.sandoval.mypokedex.domain.models.pokedex_evolution.DSpecies

data class Species(
    val name: String?,
    val url: String?
) {
    fun toDomainObject() = DSpecies(
        name = name ?: "",
        url = url ?: ""
    )
}