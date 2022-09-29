package com.sandoval.mypokedex.viewmodel.pokedex_list

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.usecase.pokedex_list.GetPokedexListUseCase
import com.sandoval.mypokedex.ui.pokedex_list.viewmodel.GetPokedexListViewModel
import com.sandoval.mypokedex.utils.UnitTest
import com.sandoval.mypokedex.utils.getOrAwaitValueTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetPokedexListViewModelTest : UnitTest() {

    @MockK
    private lateinit var getPokedexListUseCase: GetPokedexListUseCase

    private lateinit var getPokedexListViewModel: GetPokedexListViewModel

    private lateinit var pokedexList: List<DResult>

    @Before
    fun setUp() {
        pokedexList = listOf(
            DResult(
                name = "Bulbasur", url = "pokemon.com"
            )
        )

        getPokedexListViewModel = GetPokedexListViewModel(getPokedexListUseCase)
    }

    @Test
    fun `getPokedexList should return actual list`() {
        every { getPokedexListUseCase(any(), 151, any()) }.answers {
            lastArg<(Either<Failure, List<DResult>>) -> Unit>()(Either.Right(pokedexList))
        }
        getPokedexListViewModel.getResults(151)
        val res = getPokedexListViewModel.pokedexListViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexList).isEqualTo(pokedexList.map { it })
    }

    @Test
    fun `getPokedexList should show error view when error occurs`() {
        every { getPokedexListUseCase(any(), 151, any()) }.answers {
            lastArg<(Either<Failure, List<DResult>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        getPokedexListViewModel.getResults(151)
        val res = getPokedexListViewModel.pokedexListViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNotNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexList).isNull()
    }

    @Test
    fun `getPokedexList should show error connection view when a error network connection occurs`() {
        every { getPokedexListUseCase(any(), 151, any()) }.answers {
            lastArg<(Either<Failure, List<DResult>>) -> Unit>()(Either.Left(Failure.NetworkConnection))
        }
        val res = getPokedexListViewModel.pokedexListViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isTrue()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexList).isNull()
    }

}