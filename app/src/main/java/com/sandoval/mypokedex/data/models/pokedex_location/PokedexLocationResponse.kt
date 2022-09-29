package com.sandoval.mypokedex.data.models.pokedex_location

import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse

data class PokedexLocationResponse(
    val region: Region?
) {
    fun toDomainObject() = DPokedexLocationResponse(
        region = region?.toDomainObject()
    )
}