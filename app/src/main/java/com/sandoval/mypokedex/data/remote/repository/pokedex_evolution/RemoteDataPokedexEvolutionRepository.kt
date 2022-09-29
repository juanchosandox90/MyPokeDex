package com.sandoval.mypokedex.data.remote.repository.pokedex_evolution

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.repository.pokedex_evolution.IGetPokedexEvolutionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataPokedexEvolutionRepository @Inject constructor(
    private val pokedexService: PokedexService
) : IGetPokedexEvolutionRepository {
    override suspend fun getPokedexEvolution(id: Int?): Flow<Either<Failure, DPokedexEvolutionResponse>> =
        flow {
            val res = pokedexService.getPokedexEvolution(id)
            emit(
                when (res.isSuccessful) {
                    true -> {
                        res.body()?.let { it ->
                            Either.Right(it.toDomainObject())
                        } ?: Either.Left(Failure.DataError)
                    }
                    false -> Either.Left(Failure.ServerError)
                }
            )
        }
}