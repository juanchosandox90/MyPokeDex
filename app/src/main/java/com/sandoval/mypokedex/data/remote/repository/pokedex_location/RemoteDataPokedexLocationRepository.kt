package com.sandoval.mypokedex.data.remote.repository.pokedex_location

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import com.sandoval.mypokedex.domain.repository.pokedex_location.IGetPokedexLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataPokedexLocationRepository @Inject constructor(
    private val pokedexApiService: PokedexService
) : IGetPokedexLocationRepository {
    override suspend fun getPokedexLocation(id: Int?): Flow<Either<Failure, DPokedexLocationResponse>> =
        flow {
            val res = pokedexApiService.getPokedexLocation(id)
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
