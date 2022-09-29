package com.sandoval.mypokedex.domain.pokedex_evolution

import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DChain
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DEvolvesTo
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DSpecies
import com.sandoval.mypokedex.domain.repository.pokedex_evolution.IGetPokedexEvolutionRepository
import com.sandoval.mypokedex.domain.usecase.pokedex_evolution.GetPokedexEvolutionUseCase
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
class GetPokedexEvolutionUseCaseTest : UnitTest() {

    private lateinit var getPokedexEvolutionUseCase: GetPokedexEvolutionUseCase

    @MockK
    private lateinit var iGetPokedexEvolutionRepository: IGetPokedexEvolutionRepository

    @Before
    fun setUp() {
        getPokedexEvolutionUseCase = GetPokedexEvolutionUseCase(iGetPokedexEvolutionRepository)
    }

    @Test
    fun `should call getPokedexEvolution from repository`() = runTest {
        coEvery {
            iGetPokedexEvolutionRepository.getPokedexEvolution(1)
        } returns flow {
            emit(
                Either.Right(
                    DPokedexEvolutionResponse(
                        chain = DChain(
                            evolves_to = listOf(
                                DEvolvesTo(
                                    species = DSpecies(
                                        name = "Ivysaur",
                                        url = ""
                                    )
                                )
                            )
                        )
                    )
                )
            )
        }
        getPokedexEvolutionUseCase.run(1)
        coVerify(exactly = 1) { iGetPokedexEvolutionRepository.getPokedexEvolution(1) }
    }
}