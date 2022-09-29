package com.sandoval.mypokedex.viewmodel.pokedex_evolution

import com.google.common.truth.Truth
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.data.utils.Either
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DChain
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DEvolvesTo
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DSpecies
import com.sandoval.mypokedex.domain.usecase.pokedex_evolution.GetPokedexEvolutionUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.viewmodel.GetPokedexEvolutionViewModel
import com.sandoval.mypokedex.utils.UnitTest
import com.sandoval.mypokedex.utils.getOrAwaitValueTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class GetPokedexEvolutionViewModelTest : UnitTest() {

    @MockK
    private lateinit var getPokedexEvolutionUseCase: GetPokedexEvolutionUseCase

    private lateinit var getPokedexEvolutionViewModel: GetPokedexEvolutionViewModel

    private lateinit var pokedexEvolution: DPokedexEvolutionResponse

    @Before
    fun setUp() {
        pokedexEvolution = DPokedexEvolutionResponse(
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

        getPokedexEvolutionViewModel = GetPokedexEvolutionViewModel(getPokedexEvolutionUseCase)
    }

    @Test
    fun `getPokedexEvolution should return actual list`() {
        every { getPokedexEvolutionUseCase(any(), 1, any()) }.answers {
            lastArg<(Either<Failure, DPokedexEvolutionResponse>) -> Unit>()(
                Either.Right(
                    pokedexEvolution
                )
            )
        }
        getPokedexEvolutionViewModel.getPokedexEvolution(1)
        val res = getPokedexEvolutionViewModel.pokedexEvolutionViewModel.getOrAwaitValueTest()
        Truth.assertThat(res.errorMessage).isNull()
        Truth.assertThat(res.loading).isFalse()
        Truth.assertThat(res.isEmpty).isFalse()
        Truth.assertThat(res.pokedexEvolution).isEqualTo(pokedexEvolution)
    }
}