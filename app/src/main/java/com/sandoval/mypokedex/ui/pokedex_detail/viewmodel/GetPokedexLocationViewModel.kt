package com.sandoval.mypokedex.ui.pokedex_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.domain.models.pokedex_location.DPokedexLocationResponse
import com.sandoval.mypokedex.domain.usecase.pokedex_location.GetPokedexLocationUseCase
import com.sandoval.mypokedex.ui.pokedex_detail.model.state.GetPokedexLocationView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class GetPokedexLocationViewModel @Inject constructor(private val getPokedexLocationUseCase: GetPokedexLocationUseCase) :
    ViewModel() {

    private val job = Job()

    private val _pokedexLocationViewModel = MutableLiveData<GetPokedexLocationView>()
    val pokedexLocationViewModel: LiveData<GetPokedexLocationView> get() = _pokedexLocationViewModel

    fun getPokedexLocation(id: Int) {
        _pokedexLocationViewModel.value = GetPokedexLocationView(loading = true)
        getPokedexLocationUseCase(job, id) {
            it.fold(
                ::handleFailure, ::handleSuccess
            )
        }
    }

    private fun handleSuccess(pokedexLocation: DPokedexLocationResponse) {
        _pokedexLocationViewModel.value = GetPokedexLocationView(loading = false)
        _pokedexLocationViewModel.value = GetPokedexLocationView(pokedexLocation = pokedexLocation)

    }

    private fun handleFailure(failure: Failure) {
        _pokedexLocationViewModel.value = GetPokedexLocationView(loading = false)
        _pokedexLocationViewModel.value = GetPokedexLocationView(errorMessage = failure.toString())
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}