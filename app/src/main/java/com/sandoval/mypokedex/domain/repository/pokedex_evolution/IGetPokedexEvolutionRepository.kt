package com.sandoval.mypokedex.domain.repository.pokedex_evolution

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import kotlinx.coroutines.flow.Flow

interface IGetPokedexEvolutionRepository {

    //get pokedex_evolution interface
    suspend fun getPokedexEvolution(id: Int?): Flow<Either<Failure, DPokedexEvolutionResponse>>
}