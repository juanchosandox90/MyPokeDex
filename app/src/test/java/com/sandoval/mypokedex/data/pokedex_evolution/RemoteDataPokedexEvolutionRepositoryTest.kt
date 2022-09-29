package com.sandoval.mypokedex.data.pokedex_evolution

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.models.pokedex_evolution.Chain
import com.sandoval.mypokedex.data.models.pokedex_evolution.EvolvesTo
import com.sandoval.mypokedex.data.models.pokedex_evolution.PokedexEvolutionResponse
import com.sandoval.mypokedex.data.models.pokedex_evolution.Species
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_evolution.RemoteDataPokedexEvolutionRepository
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.utils.UnitTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataPokedexEvolutionRepositoryTest : UnitTest() {

    private lateinit var remoteDataPokedexEvolutionRepository: RemoteDataPokedexEvolutionRepository
    private lateinit var pokedexEvolutionResponse: PokedexEvolutionResponse

    @MockK
    private lateinit var pokedexService: PokedexService

    @MockK
    private lateinit var resultResponse: Response<PokedexEvolutionResponse>

    @Before
    fun setUp() {
        remoteDataPokedexEvolutionRepository = RemoteDataPokedexEvolutionRepository(pokedexService)
        pokedexEvolutionResponse = PokedexEvolutionResponse(
            chain = Chain(
                evolves_to = listOf(
                    EvolvesTo(
                        species = Species(
                            name = "Ivysaur",
                            url = ""
                        )
                    )
                )
            )
        )
    }

    @Test
    fun `Get pokedex evolution without name should return data error`() = runTest {
        every { resultResponse.body() } returns null
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexEvolution(null) } returns resultResponse

        val results = remoteDataPokedexEvolutionRepository.getPokedexEvolution(
            null
        )

        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }


    @Test
    fun `Get pokedex evolution with null name should return server error when response is not successful`() =
        runTest {
            every { resultResponse.isSuccessful } returns false
            coEvery { pokedexService.getPokedexEvolution(null) } returns resultResponse

            val results = remoteDataPokedexEvolutionRepository.getPokedexEvolution(null)
            results.collect { a ->
                Truth.assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get pokedex evolution service with id should return the response successful`() = runTest {
        every { resultResponse.body() } returns pokedexEvolutionResponse
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexEvolution(1) } returns resultResponse

        val results = remoteDataPokedexEvolutionRepository.getPokedexEvolution(1)
        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Right(pokedexEvolutionResponse.toDomainObject()))
        }
    }

}