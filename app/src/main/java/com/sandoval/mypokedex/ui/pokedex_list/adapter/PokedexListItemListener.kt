package com.sandoval.mypokedex.ui.pokedex_list.adapter

class PokedexListItemListener(
    val onPokedexListItemClickListener: (String, String) -> Unit
) {
    fun onPokedexListItemClicked(pokedexName: String, id: String) =
        onPokedexListItemClickListener(pokedexName, id)
}