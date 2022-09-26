package com.sandoval.mypokedex.ui.pokedex_detail.fragments

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.sandoval.mypokedex.databinding.FragmentPokedexDetailBinding
import com.sandoval.mypokedex.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexDetailFragment : BaseFragment<FragmentPokedexDetailBinding>(
    FragmentPokedexDetailBinding::inflate
) {

    private val args by navArgs<PokedexDetailFragmentArgs>()
    private var pokedexName: String? = null
    override fun initViews() {
        pokedexName = args.pokedexName
        Log.d("PokedexName", pokedexName!!)
    }

    override fun initViewModels() {

    }

}