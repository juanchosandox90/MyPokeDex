package com.sandoval.mypokedex.ui.pokedex_list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sandoval.mypokedex.databinding.ItemPokedexBinding
import com.sandoval.mypokedex.domain.models.pokedex_list.DResult
import com.sandoval.mypokedex.ui.utils.getPicUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokedexListAdapter :
    ListAdapter<PokedexListItems, RecyclerView.ViewHolder>(PokedexListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PokedexListItems.PokedexItem -> 0
            else -> throw ClassCastException("Unknown Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> PokedexListPreviewViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is PokedexListPreviewViewHolder -> {
                val pokedexItem = item as PokedexListItems.PokedexItem
                holder.bind(pokedexItem.results)
            }
        }
    }

    fun submitPokedexList(list: List<DResult>) {
        adapterScope.launch {
            val items = list.map {
                PokedexListItems.PokedexItem(it)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class PokedexListPreviewViewHolder private constructor(
        val binding: ItemPokedexBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var picture: String? = ""

        fun bind(
            results: DResult
        ) {
            binding.results = results
            loadImage(binding, results)
            binding.executePendingBindings()
        }

        private fun loadImage(binding: ItemPokedexBinding, pokemonResult: DResult) {

            picture = pokemonResult.url?.getPicUrl()

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
                            progressCircular.isVisible = false
                            return false
                        }

                    })
                    .into(pokemonItemImage)

            }
        }

        companion object {
            fun from(parent: ViewGroup): PokedexListPreviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPokedexBinding.inflate(layoutInflater, parent, false)
                return PokedexListPreviewViewHolder(binding)
            }
        }
    }
}


class PokedexListDiffCallback : DiffUtil.ItemCallback<PokedexListItems>() {
    override fun areItemsTheSame(
        oldItem: PokedexListItems, newItem: PokedexListItems
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: PokedexListItems, newItem: PokedexListItems
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class PokedexListItems {
    data class PokedexItem(val results: DResult) : PokedexListItems() {
        override val name = results.name ?: ""
    }

    abstract val name: String
}