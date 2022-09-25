package com.sandoval.mypokedex.domain.usecase.pokedex_list

import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.base.BaseUseCase
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokedexListUseCase @Inject constructor(
    private val iGetPokedexListRepository: IGetPokedexListRepository
) : BaseUseCase<Int, List<DResult>>() {
    override suspend fun run(params: Int): Flow<Either<Failure, List<DResult>>> {
        return iGetPokedexListRepository.getPokedexList(params)
    }
}