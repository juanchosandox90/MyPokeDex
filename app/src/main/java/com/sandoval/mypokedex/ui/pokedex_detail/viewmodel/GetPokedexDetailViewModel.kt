package com.sandoval.mypokedex.ui.pokedex_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.domain.usecase.pokedex_detail.GetPokedexDetailUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.model.state.GetPokedexDetailView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class GetPokedexDetailViewModel @Inject constructor(private val getPokedexDetailUseCase: GetPokedexDetailUseCase) :
    ViewModel() {

    private val job = Job()

    private val _pokedexDetailViewModel = MutableLiveData<GetPokedexDetailView>()
    val pokedexDetailViewModel: LiveData<GetPokedexDetailView> get() = _pokedexDetailViewModel

    fun getResults(name: String) {
        _pokedexDetailViewModel.value = GetPokedexDetailView(loading = true)
        getPokedexDetailUseCase(job, name) {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(pokedexDetail: DPokedexDetailResponse) {
        _pokedexDetailViewModel.value = GetPokedexDetailView(loading = false)
        _pokedexDetailViewModel.value = GetPokedexDetailView(pokedexDetail = pokedexDetail)
    }

    private fun handleFailure(failure: Failure) {
        _pokedexDetailViewModel.value = GetPokedexDetailView(loading = false)
        _pokedexDetailViewModel.value =
            GetPokedexDetailView(errorMessage = failure.toString())
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}