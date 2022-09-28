package com.sandoval.mypokedex.ui.pokedex_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.domain.models.pokedex_detail.DAbilites
import com.sandoval.mypokedex.domain.models.pokedex_detail.DMoves
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.domain.models.pokedex_detail.DTypes
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

    private val _listAbilities = MutableLiveData<List<DAbilites>?>()
    val listAbilities: LiveData<List<DAbilites>?> get() = _listAbilities

    private val _listMoves = MutableLiveData<List<DMoves>?>()
    val listMoves: LiveData<List<DMoves>?> get() = _listMoves

    private val _listTypes = MutableLiveData<List<DTypes>?>()
    val listTypes: LiveData<List<DTypes>?> get() = _listTypes

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
        _listAbilities.value = pokedexDetail.abilities
        _listMoves.value = pokedexDetail.moves
        _listTypes.value = pokedexDetail.types

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