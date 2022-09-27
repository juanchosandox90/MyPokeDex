package com.sandoval.mypokedex.data.models.pokedex_detail

import com.sandoval.mypokedex.domain.models.pokedex_detail.DSprites

data class Sprites(
    val front_default: String?
) {
    fun toDomainObject() = DSprites(
        front_default = front_default ?: ""
    )
}