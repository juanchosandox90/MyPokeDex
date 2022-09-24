package com.sandoval.mypokedex.commons

const val BASE_URL_POKEMON = "https://pokeapi.co/api/v2"
const val POKEMON_LIST_PATH = "/pokemon" //Query limits
const val POKEMON_LOCATION_PATH = "/location/{id}" // path id
const val POKEMON_MOVES_PATH = "/move/{id}" // path id
const val POKEMON_EVOLUTION_PATH = "/evolution-chain/{id}" // path id
const val POKEMON_ABILITY_PATH = "/ability/{id}" // path id
const val POKEMON_TYPE_PATH = "/type/{id}" // path id

const val BASE_URL_POKEMON_IMAGES =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id-pokemon}.png"