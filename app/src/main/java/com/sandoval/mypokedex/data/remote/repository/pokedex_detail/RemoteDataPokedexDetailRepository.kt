package com.sandoval.mypokedex.data.remote.repository.pokedex_detail

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.domain.repository.pokedex_detail.IGetPokedexDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataPokedexDetailRepository @Inject constructor(
    private val pokedexService: PokedexService
) : IGetPokedexDetailRepository {
    override suspend fun getPokedexDetail(name: String?): Flow<Either<Failure, DPokedexDetailResponse>> =
        flow {
            val res = name?.let { pokedexService.getPokedexDetail(it) }
            if (res != null) {
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
}