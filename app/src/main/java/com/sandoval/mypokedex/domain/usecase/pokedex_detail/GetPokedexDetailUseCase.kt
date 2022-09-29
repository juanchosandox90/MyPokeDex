package com.sandoval.mypokedex.domain.usecase.pokedex_detail

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.base.BaseUseCase
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.domain.repository.pokedex_detail.IGetPokedexDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokedexDetailUseCase @Inject constructor(
    private val iGetPokedexDetailRepository: IGetPokedexDetailRepository
) : BaseUseCase<String?, DPokedexDetailResponse>() {
    override suspend fun run(params: String?): Flow<Either<Failure, DPokedexDetailResponse>> {
        return iGetPokedexDetailRepository.getPokedexDetail(params)
    }
}