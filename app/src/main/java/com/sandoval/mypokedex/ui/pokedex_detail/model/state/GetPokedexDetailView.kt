package com.sandoval.mypokedex.ui.pokedex_detail.model.state

import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse

data class GetPokedexDetailView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val pokedexDetail: DPokedexDetailResponse? = null
)