package com.sandoval.mypokedex.domain.pokedex_detail

import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_detail.*
import com.sandoval.mypokedex.domain.repository.pokedex_detail.IGetPokedexDetailRepository
import com.sandoval.mypokedex.domain.usecase.pokedex_detail.GetPokedexDetailUseCase
import com.sandoval.mypokedex.utils.UnitTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPokedexDetailUseCaseTest : UnitTest() {

    private lateinit var getPokedexDetailUseCase: GetPokedexDetailUseCase

    @MockK
    private lateinit var iGetPokedexDetailRepository: IGetPokedexDetailRepository

    @Before
    fun setUp() {
        getPokedexDetailUseCase = GetPokedexDetailUseCase(iGetPokedexDetailRepository)
    }

    @Test
    fun `should call getPokedexDetail from repository`() = runTest {
        coEvery {
            iGetPokedexDetailRepository.getPokedexDetail("Bulbasaur")
        } returns flow {
            emit(
                Either.Right(
                    DPokedexDetailResponse(
                        abilities = listOf(
                            DAbilites(
                                ability = DAbility(
                                    name = "name", url = ".com"
                                )
                            )
                        ),
                        moves = listOf(
                            DMoves(
                                move = DMove(
                                    name = "name", url = ".com"
                                )
                            )
                        ),
                        name = "Bulbasaur",
                        types = listOf(
                            DTypes(
                                type = DType(
                                    name = "name", url = ".com"
                                )
                            )
                        ),
                    )
                )
            )
        }
        getPokedexDetailUseCase.run("Bulbasaur")
        coVerify(exactly = 1) { iGetPokedexDetailRepository.getPokedexDetail("Bulbasaur") }
    }
}