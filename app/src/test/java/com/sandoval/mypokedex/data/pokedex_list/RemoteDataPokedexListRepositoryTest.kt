package com.sandoval.mypokedex.data.pokedex_list

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.models.pokedex_list.PokedexListResponse
import com.sandoval.mypokedex.data.models.pokedex_list.Result
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_list.RemoteDataPokedexListRepository
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
class RemoteDataPokedexListRepositoryTest : UnitTest() {

    private lateinit var remoteDataPokedexListRepository: RemoteDataPokedexListRepository
    private lateinit var pokedexListResponse: PokedexListResponse

    @MockK
    private lateinit var pokedexService: PokedexService

    @MockK
    private lateinit var resultResponse: Response<PokedexListResponse>

    @Before
    fun setUp() {
        remoteDataPokedexListRepository = RemoteDataPokedexListRepository(pokedexService)
    }


    @Test
    fun `Pokedex List service with null response body should return data error`() = runTest {
        every { resultResponse.body() } returns null
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexList(151) } returns resultResponse
        val data = remoteDataPokedexListRepository.getPokedexList(151)
        data.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `Pokedex List Service should return server error when response is not successful`() =
        runTest {
            every { resultResponse.isSuccessful } returns false
            coEvery { pokedexService.getPokedexList(151) } returns resultResponse
            val data = remoteDataPokedexListRepository.getPokedexList(151)
            data.collect { a ->
                Truth.assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @Test
    fun `Pokedex List Service should return the employees list successful`() = runTest {
        pokedexListResponse = PokedexListResponse(
            count = 151, next = "", previous = "", results = listOf(
                Result(
                    name = "Bulbasur", url = "pokemon.com"
                )
            )
        )
        every { resultResponse.body() } returns pokedexListResponse
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexList(151) } returns resultResponse
        val data = remoteDataPokedexListRepository.getPokedexList(151)
        data.collect { a ->
            Truth.assertThat(a)
                .isEqualTo(pokedexListResponse.results?.let { Either.Right(it.map { it.toDomainObject() }) })
        }
    }
}