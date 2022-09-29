package com.sandoval.mypokedex.data.models.pokedex_location

import com.sandoval.mypokedex.domain.models.pokedex_location.DRegion

data class Region(
    val name: String?,
    val url: String?
) {
    fun toDomainObject() = DRegion(
        name = name ?: "",
        url = url ?: ""
    )
}