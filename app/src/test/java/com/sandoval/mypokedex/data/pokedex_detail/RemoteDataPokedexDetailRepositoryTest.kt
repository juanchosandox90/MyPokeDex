package com.sandoval.mypokedex.data.pokedex_detail

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.models.pokedex_detail.*
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_detail.RemoteDataPokedexDetailRepository
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
class RemoteDataPokedexDetailRepositoryTest : UnitTest() {

    private lateinit var remoteDataPokedexDetailRepository: RemoteDataPokedexDetailRepository
    private lateinit var pokedexDetailResponse: PokedexDetailResponse

    @MockK
    private lateinit var pokedexService: PokedexService

    @MockK
    private lateinit var resultResponse: Response<PokedexDetailResponse>

    @Before
    fun setUp() {
        remoteDataPokedexDetailRepository = RemoteDataPokedexDetailRepository(pokedexService)
        pokedexDetailResponse = PokedexDetailResponse(
            abilities = listOf(
                Abilities(
                    ability = Ability(
                        name = "name", url = ".com"
                    )
                )
            ),
            moves = listOf(
                Moves(
                    move = Move(
                        name = "name", url = ".com"
                    )
                )
            ),
            name = "Bulbasaur",
            types = listOf(
                Types(
                    type = Type(
                        name = "name", url = ".com"
                    )
                )
            ),
            sprites = Sprites(
                front_default = ".url"
            )
        )
    }

    @Test
    fun `Get pokedex detail without name should return data error`() = runTest {
        every { resultResponse.body() } returns null
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexDetail(null) } returns resultResponse

        val results = remoteDataPokedexDetailRepository.getPokedexDetail(
            null
        )

        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }


    @Test
    fun `Get pokedex detail with null name should return server error when response is not successful`() =
        runTest {
            every { resultResponse.isSuccessful } returns false
            coEvery { pokedexService.getPokedexDetail(null) } returns resultResponse

            val results = remoteDataPokedexDetailRepository.getPokedexDetail(null)
            results.collect { a ->
                Truth.assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get pokedex detail service with name should return the response successful`() = runTest {
        every { resultResponse.body() } returns pokedexDetailResponse
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexDetail("bulbasaur") } returns resultResponse

        val results = remoteDataPokedexDetailRepository.getPokedexDetail("bulbasaur")
        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Right(pokedexDetailResponse.toDomainObject()))
        }
    }

}