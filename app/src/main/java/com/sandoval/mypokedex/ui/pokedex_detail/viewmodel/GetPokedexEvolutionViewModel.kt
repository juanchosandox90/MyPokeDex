package com.sandoval.mypokedex.ui.pokedex_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.domain.models.pokedex_evolution.DPokedexEvolutionResponse
import com.sandoval.mypokedex.domain.usecase.pokedex_evolution.GetPokedexEvolutionUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.model.state.GetPokedexEvolutionView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class GetPokedexEvolutionViewModel @Inject constructor(private val getPokedexEvolutionUseCase: GetPokedexEvolutionUseCase) :
    ViewModel() {

    private val job = Job()

    private val _pokedexEvolutionViewModel = MutableLiveData<GetPokedexEvolutionView>()
    val pokedexEvolutionViewModel: LiveData<GetPokedexEvolutionView> get() = _pokedexEvolutionViewModel

    fun getPokedexEvolution(id: Int) {
        _pokedexEvolutionViewModel.value = GetPokedexEvolutionView(loading = true)
        getPokedexEvolutionUseCase(job, id) {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(pokedexEvolution: DPokedexEvolutionResponse) {
        _pokedexEvolutionViewModel.value = GetPokedexEvolutionView(loading = false)
        _pokedexEvolutionViewModel.value =
            GetPokedexEvolutionView(pokedexEvolution = pokedexEvolution)

    }

    private fun handleFailure(failure: Failure) {
        _pokedexEvolutionViewModel.value = GetPokedexEvolutionView(loading = false)
        _pokedexEvolutionViewModel.value =
            GetPokedexEvolutionView(errorMessage = failure.toString())
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}