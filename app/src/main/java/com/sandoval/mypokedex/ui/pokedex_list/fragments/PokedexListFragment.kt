package com.sandoval.mypokedex.ui.pokedex_list.fragments

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.mypokedex.databinding.FragmentPokedexListBinding
import com.sandoval.mypokedex.ui.base.BaseFragment
import com.sandoval.mypokedex.ui.pokedex_list.adapter.PokedexListAdapter
import com.sandoval.mypokedex.ui.pokedex_list.viewmodel.GetPokedexListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexListFragment : BaseFragment<FragmentPokedexListBinding>(
    FragmentPokedexListBinding::inflate
) {

    private val getPokedexListViewModel: GetPokedexListViewModel by viewModels()
    private lateinit var pokedexListAdapter: PokedexListAdapter

    override fun initViews() {
        pokedexListAdapter = PokedexListAdapter()
        pokedexListAdapter.apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    binding.pokedexList.smoothScrollToPosition(0)
                }
            })
        }
        setupPokedexListRecycler()
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
                    binding.pokedexList.visibility = View.GONE
                    Log.d("PokedexList Empty", "Empty...")
                }
                it.pokedexList != null -> {
                    hideLoading()
                    pokedexListAdapter.submitPokedexList(it.pokedexList)
                    Log.d("PokedexList", it.pokedexList.toString())
                }
                it.errorMessage != null -> {
                    hideLoading()
                    binding.pokedexList.visibility = View.GONE
                    Log.e("PokedexList Error", it.errorMessage.toString())
                }
            }
        }
    }

    private fun setupPokedexListRecycler() {
        binding.pokedexList.visibility = View.VISIBLE
        binding.pokedexList.adapter = pokedexListAdapter
    }

    private fun showLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.GONE
    }
}
