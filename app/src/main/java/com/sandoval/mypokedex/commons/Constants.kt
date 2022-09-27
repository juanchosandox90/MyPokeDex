package com.sandoval.mypokedex.commons

const val BASE_URL_POKEMON = "https://pokeapi.co/api/v2/"
const val POKEMON_LIST_PATH = "pokemon" //Query limits
const val POKEMON_DETAIL_PATH = "pokemon{name}" //Query limits
const val POKEMON_LOCATION_PATH = "location/{name}" // path id
const val POKEMON_EVOLUTION_PATH = "evolution-chain/{name}" // path id

const val BASE_URL_POKEMON_IMAGES =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id-pokemon}.png"