package com.sandoval.mypokedex.data.pokedex_location

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.models.pokedex_location.PokedexLocationResponse
import com.sandoval.mypokedex.data.models.pokedex_location.Region
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.remote.api.PokedexService
import com.sandoval.mypokedex.data.remote.repository.pokedex_location.RemoteDataPokedexLocationRepository
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
class RemoteDataPokedexLocationRepositoryTest : UnitTest() {

    private lateinit var remoteDataPokedexLocationRepository: RemoteDataPokedexLocationRepository
    private lateinit var pokedexLocationResponse: PokedexLocationResponse

    @MockK
    private lateinit var pokedexService: PokedexService

    @MockK
    private lateinit var resultResponse: Response<PokedexLocationResponse>

    @Before
    fun setUp() {
        remoteDataPokedexLocationRepository = RemoteDataPokedexLocationRepository(pokedexService)
        pokedexLocationResponse = PokedexLocationResponse(
            region = Region(
                name = "Sinooh", url = ".com"
            )
        )
    }

    @Test
    fun `Get pokedex location without name should return data error`() = runTest {
        every { resultResponse.body() } returns null
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexLocation(null) } returns resultResponse

        val results = remoteDataPokedexLocationRepository.getPokedexLocation(
            null
        )

        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Left(Failure.DataError))
        }
    }


    @Test
    fun `Get pokedex location with null name should return server error when response is not successful`() =
        runTest {
            every { resultResponse.isSuccessful } returns false
            coEvery { pokedexService.getPokedexLocation(null) } returns resultResponse

            val results = remoteDataPokedexLocationRepository.getPokedexLocation(null)
            results.collect { a ->
                Truth.assertThat(a).isEqualTo(Either.Left(Failure.ServerError))
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `Get pokedex location service with id should return the response successful`() = runTest {
        every { resultResponse.body() } returns pokedexLocationResponse
        every { resultResponse.isSuccessful } returns true
        coEvery { pokedexService.getPokedexLocation(1) } returns resultResponse

        val results = remoteDataPokedexLocationRepository.getPokedexLocation(1)
        results.collect { a ->
            Truth.assertThat(a).isEqualTo(Either.Right(pokedexLocationResponse.toDomainObject()))
        }
    }
}