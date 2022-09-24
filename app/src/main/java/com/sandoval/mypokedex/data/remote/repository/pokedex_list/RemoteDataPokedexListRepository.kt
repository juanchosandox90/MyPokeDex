package com.sandoval.mypokedex.data.remote.repository.pokedex_list

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataPokedexListRepository @Inject constructor(
    private val pokedexApiService: PokedexService
): IGetPokedexListRepository {

    override suspend fun getPokedexList(limit: Int): Flow<Either<Failure, List<DResult>>> {
        // Repository will use kotlin flow to emit the data from the service
        // Repository will use a helper util class Either.kt to throw a left or right result.
        TODO("Not yet implemented")
    }
}