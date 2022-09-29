package com.sandoval.mypokedex.data.remote.api

import com.sandoval.mypokedex.commons.POKEMON_DETAIL_PATH
import com.sandoval.mypokedex.commons.POKEMON_EVOLUTION_PATH
import com.sandoval.mypokedex.commons.POKEMON_LIST_PATH
import com.sandoval.mypokedex.commons.POKEMON_LOCATION_PATH
import com.sandoval.mypokedex.data.models.pokedex_detail.PokedexDetailResponse
import com.sandoval.mypokedex.data.models.pokedex_evolution.PokedexEvolutionResponse
import com.sandoval.mypokedex.data.models.pokedex_list.PokedexListResponse
import com.sandoval.mypokedex.data.models.pokedex_location.PokedexLocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexService {

    //Get Pokedex List
    @GET(POKEMON_LIST_PATH)
    suspend fun getPokedexList(
        @Query("limit") limit: Int
    ): Response<PokedexListResponse>

    //Get Pokedex List
    @GET(POKEMON_DETAIL_PATH)
    suspend fun getPokedexDetail(
        @Path("name") name: String?
    ): Response<PokedexDetailResponse>

    //Get Pokedex Evolution
    @GET(POKEMON_EVOLUTION_PATH)
    suspend fun getPokedexEvolution(
        @Path("id") id: Int?
    ): Response<PokedexEvolutionResponse>

    //Get Pokedex Location
    @GET(POKEMON_LOCATION_PATH)
    suspend fun getPokedexLocation(
        @Path("id") id: Int?
    ): Response<PokedexLocationResponse>
}