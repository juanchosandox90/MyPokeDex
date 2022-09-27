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
import com.sandoval.mypokedex.ui.pokedex_detail.viewmodel.GetPokedexDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokedexDetailFragment : BaseFragment<FragmentPokedexDetailBinding>(
    FragmentPokedexDetailBinding::inflate
) {

    private val getPokedexDetailViewModel: GetPokedexDetailViewModel by viewModels()

    private val args by navArgs<PokedexDetailFragmentArgs>()
    private var pokedexName: String? = null
    override fun initViews() {
        pokedexName = args.pokedexName
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = pokedexName
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
                    binding.pokemonItemTitle.text = it.pokedexDetail.name
                    Log.d("PokedexDetail", it.pokedexDetail.toString())
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

}