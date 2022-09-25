package com.sandoval.mypokedex.ui.pokedex_list.model.state

import com.sandoval.mypokedex.domain.models.pokedex_list.DResult

data class GetPokedexListView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val pokedexList: List<DResult>? = null
)