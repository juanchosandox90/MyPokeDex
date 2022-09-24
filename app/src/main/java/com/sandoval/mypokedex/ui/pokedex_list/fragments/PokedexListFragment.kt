package com.sandoval.mypokedex.ui.pokedex_list.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sandoval.mypokedex.R
import com.sandoval.mypokedex.databinding.FragmentPokedexListBinding

class PokedexListFragment : Fragment() {
    private var _fragmentPokedexListBinding: FragmentPokedexListBinding? = null
    private val fragmentPokedexListBinding get() = _fragmentPokedexListBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentPokedexListBinding = FragmentPokedexListBinding.inflate(inflater, container, false)

        fragmentPokedexListBinding.goToDetail.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_pokedex_list_fragment_to_navigation_pokedex_detail_fragment)
        }

        return fragmentPokedexListBinding.root
    }
}
