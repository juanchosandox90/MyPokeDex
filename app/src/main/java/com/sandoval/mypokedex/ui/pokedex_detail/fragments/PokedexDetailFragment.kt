package com.sandoval.mypokedex.ui.pokedex_detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sandoval.mypokedex.databinding.FragmentPokedexDetailBinding

class PokedexDetailFragment : Fragment() {

    private var _fragmentPokedexDetailBinding: FragmentPokedexDetailBinding? = null
    private val fragmentPokedexDetailBinding get() = _fragmentPokedexDetailBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentPokedexDetailBinding =
            FragmentPokedexDetailBinding.inflate(inflater, container, false)
        return fragmentPokedexDetailBinding.root
    }
}