package com.sandoval.mypokedex.domain.pokedex_location

import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import com.sandoval.mypokedex.domain.models.pokedex_location.DRegion
import com.sandoval.mypokedex.domain.repository.pokedex_location.IGetPokedexLocationRepository
import com.sandoval.mypokedex.domain.usecase.pokedex_location.GetPokedexLocationUseCase
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
class GetPokedexLocationUseCaseTest : UnitTest() {

    private lateinit var getPokedexLocationUseCase: GetPokedexLocationUseCase

    @MockK
    private lateinit var iGetPokedexLocationRepository: IGetPokedexLocationRepository

    @Before
    fun setUp() {
        getPokedexLocationUseCase = GetPokedexLocationUseCase(iGetPokedexLocationRepository)
    }

    @Test
    fun `should call getPokedexLocation from repository`() = runTest {
        coEvery {
            iGetPokedexLocationRepository.getPokedexLocation(1)
        } returns flow {
            emit(
                Either.Right(
                    DPokedexLocationResponse(
                        region = DRegion(
                            name = "Sinooh",
                            url = ".com"
                        )
                    )
                )
            )
        }
        getPokedexLocationUseCase.run(1)
        coVerify(exactly = 1) { iGetPokedexLocationRepository.getPokedexLocation(1) }
    }
}