package com.sandoval.mypokedex.ui.pokedex_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandoval.mypokedex.data.network.Failure
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.domain.usecase.pokedex_list.GetPokedexListUseCase
import com.sandoval.mypokedex.ui.pokedex_list.model.state.GetPokedexListView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class GetPokedexListViewModel @Inject constructor(private val getPokedexListUseCase: GetPokedexListUseCase) :
    ViewModel() {

    private val job = Job()

    private val _pokedexListViewModel = MutableLiveData<GetPokedexListView>()
    val pokedexListViewModel: LiveData<GetPokedexListView> get() = _pokedexListViewModel

    init {
        getResults(151)
    }

    fun getResults(limit: Int) {
        _pokedexListViewModel.value = GetPokedexListView(loading = true)
        getPokedexListUseCase(job, limit) {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(pokedexList: List<DResult>) {
        if (pokedexList.isEmpty()) {
            _pokedexListViewModel.value = GetPokedexListView(loading = false)
            _pokedexListViewModel.value = GetPokedexListView(isEmpty = true)
        } else {
            _pokedexListViewModel.value = GetPokedexListView(loading = false)
            _pokedexListViewModel.value =
                GetPokedexListView(pokedexList = pokedexList)
        }
    }

    private fun handleFailure(failure: Failure) {
        _pokedexListViewModel.value = GetPokedexListView(loading = false)
        _pokedexListViewModel.value =
            GetPokedexListView(errorMessage = failure.toString())
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}