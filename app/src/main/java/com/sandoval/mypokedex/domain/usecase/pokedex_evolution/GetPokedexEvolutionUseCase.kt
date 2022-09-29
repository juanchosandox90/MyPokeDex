package com.sandoval.mypokedex.domain.usecase.pokedex_evolution

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.base.BaseUseCase
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.repository.pokedex_evolution.IGetPokedexEvolutionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokedexEvolutionUseCase @Inject constructor(
    private val iGetPokedexEvolutionRepository: IGetPokedexEvolutionRepository
) : BaseUseCase<Int?, DPokedexEvolutionResponse>() {
    override suspend fun run(params: Int?): Flow<Either<Failure, DPokedexEvolutionResponse>> {
        return iGetPokedexEvolutionRepository.getPokedexEvolution(params)
    }
}
