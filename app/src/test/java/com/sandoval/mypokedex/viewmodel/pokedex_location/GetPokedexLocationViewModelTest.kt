package com.sandoval.mypokedex.viewmodel.pokedex_location

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import com.sandoval.mypokedex.domain.models.pokedex_location.DRegion
import com.sandoval.mypokedex.domain.usecase.pokedex_location.GetPokedexLocationUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.viewmodel.GetPokedexLocationViewModel
import com.sandoval.mypokedex.utils.UnitTest
import com.sandoval.mypokedex.utils.getOrAwaitValueTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetPokedexLocationViewModelTest : UnitTest() {

    @MockK
    private lateinit var getPokedexLocationUseCase: GetPokedexLocationUseCase

    private lateinit var getPokedexLocationViewModel: GetPokedexLocationViewModel

    private lateinit var pokedexLocation: DPokedexLocationResponse

    @Before
    fun setUp() {
        pokedexLocation = DPokedexLocationResponse(
            region = DRegion(
                name = "Sinooh", url = ".com"
            )
        )

        getPokedexLocationViewModel = GetPokedexLocationViewModel(getPokedexLocationUseCase)
    }

    @Test
    fun `getPokedexLocation should return actual list`() {
        every { getPokedexLocationUseCase(any(), 1, any()) }.answers {
            lastArg<(Either<Failure, DPokedexLocationResponse>) -> Unit>()(
                Either.Right(
                    pokedexLocation
                )
            )
        }
        getPokedexLocationViewModel.getPokedexLocation(1)
        val res = getPokedexLocationViewModel.pokedexLocationViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexLocation).isEqualTo(pokedexLocation)
    }

    @Test
    fun `getPokedexLocation should show error view when error occurs`() {
        every { getPokedexLocationUseCase(any(), 1, any()) }.answers {
            lastArg<(Either<Failure, DPokedexLocationResponse>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        getPokedexLocationViewModel.getPokedexLocation(1)
        val res = getPokedexLocationViewModel.pokedexLocationViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexLocation).isNull()
    }

    @Test
    fun `getPokedexLocation should show error connection view when a error network connection occurs`() {
        every { getPokedexLocationUseCase(any(), 1, any()) }.answers {
            lastArg<(Either<Failure, DPokedexLocationResponse>) -> Unit>()(Either.Left(Failure.NetworkConnection))
        }
        getPokedexLocationViewModel.getPokedexLocation(1)
        val res = getPokedexLocationViewModel.pokedexLocationViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexLocation).isNull()
    }
}