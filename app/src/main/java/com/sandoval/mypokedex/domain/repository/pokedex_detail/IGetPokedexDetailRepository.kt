package com.sandoval.mypokedex.domain.repository.pokedex_detail

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import kotlinx.coroutines.flow.Flow

interface IGetPokedexDetailRepository {

    //get pokedex_detail interface
    suspend fun getPokedexDetail(name: String?): Flow<Either<Failure, DPokedexDetailResponse>>
}