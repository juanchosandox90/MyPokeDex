package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DAbilites

data class Abilities(
    val ability: Ability?
) {
    fun toDomainObject() = DAbilites(
        ability = ability?.toDomainObject()
    )
}