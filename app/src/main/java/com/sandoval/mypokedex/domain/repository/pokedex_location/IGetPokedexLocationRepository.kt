package com.sandoval.mypokedex.domain.repository.pokedex_location

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import kotlinx.coroutines.flow.Flow

interface IGetPokedexLocationRepository {


    //get pokedex_location interface
    suspend fun getPokedexLocation(id: Int?): Flow<Either<Failure, DPokedexLocationResponse>>
}