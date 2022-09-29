package com.sandoval.mypokedex.ui.pokedex_list.fragments

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.mypokedex.databinding.FragmentPokedexListBinding
import com.sandoval.mypokedex.ui.base.BaseFragment
import com.sandoval.mypokedex.ui.pokedex_list.adapter.PokedexListAdapter
import com.sandoval.mypokedex.ui.pokedex_list.adapter.PokedexListItemListener
import com.sandoval.mypokedex.ui.pokedex_list.viewmodel.GetPokedexListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexListFragment : BaseFragment<FragmentPokedexListBinding>(
    FragmentPokedexListBinding::inflate
) {

    private val getPokedexListViewModel: GetPokedexListViewModel by viewModels()
    private lateinit var pokedexListAdapter: PokedexListAdapter

    override fun initViews() {
        pokedexListAdapter = PokedexListAdapter(
            onPokedexListItemListener = PokedexListItemListener { pokedexName, id ->
                val action =
                    PokedexListFragmentDirections.actionNavigationPokedexListFragmentToNavigationPokedexDetailFragment(
                        pokedexName,
                        id
                    )
                findNavController().navigate(action)
            }
        )
        pokedexListAdapter.apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    binding.pokedexList.smoothScrollToPosition(0)
                }
            })
        }

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getPokedexListViewModel.filterWithQuery(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getPokedexListViewModel.filterWithQuery(newText ?: "")
                return false
            }

        })

        setupPokedexListRecycler()
    }

    override fun initViewModels() {
        getPokedexListViewModel.pokedexListViewModel.observe(viewLifecycleOwner) {
            when {
                it.loading -> {
                    showLoading()
                }
                it.isEmpty -> {
                    hideLoading()
                    binding.pokedexList.visibility = View.GONE
                }
                it.pokedexList != null -> {
                    hideLoading()
                    pokedexListAdapter.submitPokedexList(it.pokedexList)
                }
                it.errorMessage != null -> {
                    hideLoading()
                    binding.pokedexList.visibility = View.GONE
                    setupGeneralErrorView()
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

    private fun setupGeneralErrorView() {
        binding.pokedexList.visibility = View.GONE
        binding.generalError.generalIssues.visibility = View.VISIBLE
        binding.generalError.reload.setOnClickListener {
            getPokedexListViewModel.getResults(151)
            binding.generalError.generalIssues.visibility = View.GONE
            showLoading()
        }
    }
}
