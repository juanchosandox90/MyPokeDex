package com.sandoval.mypokedex.viewmodel.pokedex_detail

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_detail.*
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.usecase.pokedex_detail.GetPokedexDetailUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.viewmodel.GetPokedexDetailViewModel
import com.sandoval.mypokedex.utils.UnitTest
import com.sandoval.mypokedex.utils.getOrAwaitValueTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetPokedexDetailViewModelTest : UnitTest() {

    @MockK
    private lateinit var getPokedexDetailUseCase: GetPokedexDetailUseCase

    private lateinit var getPokedexDetailViewModel: GetPokedexDetailViewModel

    private lateinit var pokedexDetail: DPokedexDetailResponse

    @Before
    fun setUp() {
        pokedexDetail = DPokedexDetailResponse(
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
            sprites = DSprites(
                front_default = ".url"
            )
        )

        getPokedexDetailViewModel = GetPokedexDetailViewModel(getPokedexDetailUseCase)
    }

    @Test
    fun `getPokedexDetail should return actual list`() {
        every { getPokedexDetailUseCase(any(), "Bulbasaur", any()) }.answers {
            lastArg<(Either<Failure, DPokedexDetailResponse>) -> Unit>()(Either.Right(pokedexDetail))
        }
        getPokedexDetailViewModel.getResults("Bulbasaur")
        val res = getPokedexDetailViewModel.pokedexDetailViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexDetail).isEqualTo(pokedexDetail)
    }

    @Test
    fun `getPokedexDetail should show error view when error occurs`() {
        every { getPokedexDetailUseCase(any(), "Bulbasaur", any()) }.answers {
            lastArg<(Either<Failure, List<DResult>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        getPokedexDetailViewModel.getResults("Bulbasaur")
        val res = getPokedexDetailViewModel.pokedexDetailViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexDetail).isNull()
    }

    @Test
    fun `getPokedexDetail should show error connection view when a error network connection occurs`() {
        every { getPokedexDetailUseCase(any(), "Bulbasaur", any()) }.answers {
            lastArg<(Either<Failure, List<DResult>>) -> Unit>()(Either.Left(Failure.NetworkConnection))
        }
        getPokedexDetailViewModel.getResults("Bulbasaur")
        val res = getPokedexDetailViewModel.pokedexDetailViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexDetail).isNull()
    }
}