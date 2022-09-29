package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DAbility

data class Ability(
    val name: String?,
    val url: String?
) {
    fun toDomainObject() = DAbility(
        name = name ?: "",
        url = url ?: ""
    )
}