package com.sandoval.mypokedex.ui.pokedex_detail.model.state

import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse

data class GetPokedexEvolutionView(
    val loading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val pokedexEvolution: DPokedexEvolutionResponse? = null
)