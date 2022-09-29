package com.sandoval.mypokedex.domain.usecase.pokedex_location

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.base.BaseUseCase
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import com.sandoval.mypokedex.domain.repository.pokedex_location.IGetPokedexLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokedexLocationUseCase @Inject constructor(
    private val IGetPokedexLocationRepository: IGetPokedexLocationRepository
) : BaseUseCase<Int?, DPokedexLocationResponse>() {
    override suspend fun run(params: Int?): Flow<Either<Failure, DPokedexLocationResponse>> {
        return IGetPokedexLocationRepository.getPokedexLocation(params)
    }
}