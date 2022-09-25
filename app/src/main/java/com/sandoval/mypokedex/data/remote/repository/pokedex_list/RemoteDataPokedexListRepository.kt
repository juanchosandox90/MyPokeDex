package com.sandoval.mypokedex.data.remote.repository.pokedex_list

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataPokedexListRepository @Inject constructor(
    private val pokedexApiService: PokedexService
) : IGetPokedexListRepository {

    override suspend fun getPokedexList(limit: Int): Flow<Either<Failure, List<DResult>>> = flow {
        val res = pokedexApiService.getPokedexList(limit)
        emit(
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let {
                        it.results?.let { it1 -> Either.Right(it1.map { a -> a.toDomainObject() }) }
                    } ?: Either.Left(Failure.DataError)
                }
                false -> {
                    Either.Left(Failure.ServerError)
                }
            }
        )
    }
}