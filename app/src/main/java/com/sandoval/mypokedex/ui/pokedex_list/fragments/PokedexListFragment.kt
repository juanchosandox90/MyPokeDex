package com.sandoval.mypokedex.ui.pokedex_list.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sandoval.mypokedex.R
import com.sandoval.mypokedex.databinding.FragmentPokedexListBinding
import com.sandoval.mypokedex.ui.pokedex_list.viewmodel.GetPokedexListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexListFragment : Fragment() {

    private val getPokedexListViewModel: GetPokedexListViewModel by viewModels()
    private var _fragmentPokedexListBinding: FragmentPokedexListBinding? = null
    private val fragmentPokedexListBinding get() = _fragmentPokedexListBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _fragmentPokedexListBinding = FragmentPokedexListBinding.inflate(inflater, container, false)

        fragmentPokedexListBinding.goToDetail.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_pokedex_list_fragment_to_navigation_pokedex_detail_fragment)
        }

        getPokedexListViewModel.pokedexListViewModel.observe(viewLifecycleOwner) {
            when {
                it.loading -> {
                    showLoading()
                    Log.d("PokedexList", "Loading...")
                }
                it.isEmpty -> {
                    hideLoading()
                    Log.d("PokedexList Empty", "Empty...")
                }
                it.pokedexList != null -> {
                    hideLoading()
                    Log.d("PokedexList", it.pokedexList.toString())
                }
                it.errorMessage != null -> {
                    hideLoading()
                    Log.e("PokedexList Error", it.errorMessage.toString())
                }
            }
        }

        return fragmentPokedexListBinding.root
    }

    private fun showLoading() {
        fragmentPokedexListBinding.loadingSpinner.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        fragmentPokedexListBinding.loadingSpinner.loadingContainer.visibility = View.GONE
    }
}
