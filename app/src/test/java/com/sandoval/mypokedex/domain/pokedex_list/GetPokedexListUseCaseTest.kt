package com.sandoval.mypokedex.domain.pokedex_list

import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.repository.pokedex_list.IGetPokedexListRepository
import com.sandoval.mypokedex.domain.usecase.pokedex_list.GetPokedexListUseCase
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
class GetPokedexListUseCaseTest : UnitTest() {

    private lateinit var getPokedexListUseCase: GetPokedexListUseCase

    @MockK
    private lateinit var iGetPokedexListRepository: IGetPokedexListRepository

    @Before
    fun setUp() {
        getPokedexListUseCase = GetPokedexListUseCase(iGetPokedexListRepository)
    }

    @Test
    fun `should call getPokedexList from repository`() = runTest {
        coEvery {
            iGetPokedexListRepository.getPokedexList(151)
        } returns flow {
            emit(
                Either.Right(
                    listOf(
                        DResult(
                            name = "Bulbasur", url = "pokemon.com"
                        )
                    )
                )
            )
        }
        getPokedexListUseCase.run(151)
        coVerify(exactly = 1) { iGetPokedexListRepository.getPokedexList(151) }
    }
}