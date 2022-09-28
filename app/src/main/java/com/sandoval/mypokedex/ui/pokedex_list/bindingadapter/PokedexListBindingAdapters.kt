package com.sandoval.mypokedex.ui.pokedex_list.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult

object PokedexListBindingAdapters {

    @BindingAdapter("android:pokemonName")
    @JvmStatic
    fun loadImageFromUrl(
        textView: TextView, name: DResult
    ) {
        val pokemonName = name.name
        textView.text = pokemonName.toString().replaceFirstChar { it.uppercase() }
    }
}