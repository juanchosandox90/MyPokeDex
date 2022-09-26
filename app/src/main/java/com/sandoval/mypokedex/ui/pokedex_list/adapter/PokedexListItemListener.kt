package com.sandoval.mypokedex.ui.pokedex_list.adapter

class PokedexListItemListener(
    val onPokedexListItemClickListener: (String) -> Unit
) {
    fun onPokedexListItemClicked(pokedexName: String) =
        onPokedexListItemClickListener(pokedexName)
}