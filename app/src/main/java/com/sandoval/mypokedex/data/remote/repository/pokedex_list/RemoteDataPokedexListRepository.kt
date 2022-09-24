package com.sandoval.mypokedex.data.remote.repository.pokedex_list

import com.sandoval.mypokedex.data.remote.api.PokedexService
import javax.inject.Inject

class RemoteDataPokedexListRepository @Inject constructor(
    private val pokedexApiService: PokedexService
) {
    // Here the project will implement a repository interface from the domain layer
    // Repository will use kotlin flow to emit the data from the service
    // Repository will use a helper util class Either.kt to throw a left or right result.
}