package com.sandoval.mypokedex.ui.pokedex_detail.model.state

import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse

data class GetPokedexLocationView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val pokedexLocation: DPokedexLocationResponse? = null
)