package com.sandoval.mypokedex.ui.pokedex_list.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sandoval.mypokedex.R
import com.sandoval.mypokedex.databinding.FragmentPokedexListBinding
import com.sandoval.mypokedex.ui.base.BaseFragment
import com.sandoval.mypokedex.ui.pokedex_list.viewmodel.GetPokedexListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexListFragment : BaseFragment<FragmentPokedexListBinding>(
    FragmentPokedexListBinding::inflate
) {

    private val getPokedexListViewModel: GetPokedexListViewModel by viewModels()

    override fun initViews() {

        binding.goToDetail.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_pokedex_list_fragment_to_navigation_pokedex_detail_fragment)
        }
    }

    override fun initViewModels() {
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
    }

    private fun showLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.GONE
    }
}
