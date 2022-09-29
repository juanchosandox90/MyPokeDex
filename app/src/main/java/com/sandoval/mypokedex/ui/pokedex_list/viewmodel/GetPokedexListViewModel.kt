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

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    private val _listOriginalOfHoldings = MutableLiveData<MutableList<DResult>>()
    val listOriginalOfHoldings: LiveData<MutableList<DResult>> get() = _listOriginalOfHoldings

    private val _listFilteredOfHoldings = MutableLiveData<MutableList<DResult>>()
    val listFilteredOfHoldings: LiveData<MutableList<DResult>> get() = _listFilteredOfHoldings

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

    fun filterWithQuery(query: String) {
        val currentQuery = _query.value ?: ""

        if (query != currentQuery)
            _query.value = query

        val originalList = _listOriginalOfHoldings.value ?: mutableListOf()

        _listFilteredOfHoldings.value =
            if (query.isNotEmpty())
                originalList.filter { it.name?.contains(query) ?: false }.toMutableList()
            else
                originalList
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}