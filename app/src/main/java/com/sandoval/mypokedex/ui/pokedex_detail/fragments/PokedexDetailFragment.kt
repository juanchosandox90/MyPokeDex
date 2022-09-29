package com.sandoval.mypokedex.ui.pokedex_detail.fragments

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sandoval.mypokedex.R
import com.sandoval.mypokedex.databinding.FragmentPokedexDetailBinding
import com.sandoval.mypokedex.domain.models.pokedex_detail.DPokedexDetailResponse
import com.sandoval.mypokedex.ui.base.BaseFragment
import com.sandoval.mypokedex.ui.pokedex_detail.adapters.AbilitiesAdapter
import com.sandoval.mypokedex.ui.pokedex_detail.adapters.MovesAdapter
import com.sandoval.mypokedex.ui.pokedex_detail.adapters.TypesAdapter
import com.sandoval.mypokedex.ui.pokedex_detail.viewmodel.GetPokedexDetailViewModel
import com.sandoval.mypokedex.ui.utils.extractId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexDetailFragment : BaseFragment<FragmentPokedexDetailBinding>(
    FragmentPokedexDetailBinding::inflate
) {

    private val getPokedexDetailViewModel: GetPokedexDetailViewModel by viewModels()
    private lateinit var abilitiesAdapter: AbilitiesAdapter
    private lateinit var typesAdapter: TypesAdapter
    private lateinit var movesAdapter: MovesAdapter

    private val args by navArgs<PokedexDetailFragmentArgs>()
    private var pokedexName: String? = null
    private var idUrl: String? = null
    override fun initViews() {
        pokedexName = args.pokedexName
        idUrl = args.id
        val id = idUrl!!.extractId()
        Log.d("Id", id.toString())
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = pokedexName

        abilitiesAdapter = AbilitiesAdapter()
        typesAdapter = TypesAdapter()
        movesAdapter = MovesAdapter()

        abilitiesAdapter.apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    binding.abilitiesList.smoothScrollToPosition(0)
                }
            })
        }

        typesAdapter.apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    binding.typesList.smoothScrollToPosition(0)
                }
            })
        }

        movesAdapter.apply {
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    binding.movesList.smoothScrollToPosition(0)
                }
            })
        }

        setupAbilitiesRecycler()
        setupTypesRecycler()
        setupMovesRecycler()
    }

    override fun initViewModels() {
        getPokedexDetailViewModel.getResults(pokedexName!!)
        getPokedexDetailViewModel.pokedexDetailViewModel.observe(viewLifecycleOwner) {
            when {
                it.loading -> {
                    showLoading()
                }
                it.isEmpty -> {
                    hideLoading()
                }
                it.pokedexDetail != null -> {
                    hideLoading()
                    loadImage(it.pokedexDetail)
                    val name = it.pokedexDetail.name.toString().replaceFirstChar { it.uppercase() }
                    binding.pokemonItemTitle.text = name
                }
                it.errorMessage != null -> {
                    hideLoading()
                }
            }
        }

        getPokedexDetailViewModel.listAbilities.observe(viewLifecycleOwner) { listAbilities ->
            if (listAbilities != null) {
                abilitiesAdapter.submitPokedexAbilities(listAbilities)
            } else {
                binding.abilitiesList.visibility = View.GONE
            }
        }

        getPokedexDetailViewModel.listMoves.observe(viewLifecycleOwner) { listMoves ->
            if (listMoves != null) {
                movesAdapter.submitPokedexMoves(listMoves)
            } else {
                binding.movesList.visibility = View.GONE
            }
        }

        getPokedexDetailViewModel.listTypes.observe(viewLifecycleOwner) { listTypes ->
            if (listTypes != null) {
                typesAdapter.submitPokedexTypes(listTypes)
            } else {
                binding.typesList.visibility = View.GONE
            }
        }
    }

    private fun showLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingSpinner.loadingContainer.visibility = View.GONE
    }

    private fun loadImage(
        detail: DPokedexDetailResponse
    ) {
        var dominantColor = 0
        val picture = detail.sprites?.front_default

        binding.apply {
            Glide.with(root)
                .load(picture)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressCircular.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        val drawable = resource as BitmapDrawable
                        val bitmap = drawable.bitmap
                        Palette.Builder(bitmap).generate {
                            it?.let { palette ->
                                dominantColor = palette.getDominantColor(
                                    ContextCompat.getColor(
                                        root.context,
                                        R.color.white
                                    )
                                )

                                pokemonItemImage.setBackgroundColor(dominantColor)


                            }
                        }
                        progressCircular.isVisible = false
                        return false
                    }

                })
                .into(pokemonItemImage)

        }
    }

    private fun setupAbilitiesRecycler() {
        binding.abilitiesList.visibility = View.VISIBLE
        binding.abilitiesList.adapter = abilitiesAdapter
    }

    private fun setupTypesRecycler() {
        binding.typesList.visibility = View.VISIBLE
        binding.typesList.adapter = typesAdapter
    }

    private fun setupMovesRecycler() {
        binding.movesList.visibility = View.VISIBLE
        binding.movesList.adapter = movesAdapter
    }

}