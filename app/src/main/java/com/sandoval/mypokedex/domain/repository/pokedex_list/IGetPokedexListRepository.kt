package com.sandoval.mypokedex.domain.repository.pokedex_list

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import kotlinx.coroutines.flow.Flow

interface IGetPokedexListRepository {

    //get pokedex_list interface
    suspend fun getPokedexList(limit: Int): Flow<Either<Failure, List<DResult>>>
}