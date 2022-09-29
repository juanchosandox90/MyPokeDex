package com.sandoval.mypokedex.ui.pokedex_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.mypokedex.databinding.ItemMoveBinding
import com.sandoval.mypokedex.domain.models.pokedex_detail.DMove
import com.sandoval.mypokedex.domain.models.pokedex_detail.DMoves
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovesAdapter :
    ListAdapter<MovesListItems, RecyclerView.ViewHolder>(MovesListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovesListItems.MoveItem -> 0
            else -> throw ClassCastException("Unknown Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> MovesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is MovesViewHolder -> {
                val move = item as MovesListItems.MoveItem
                move.moves?.let {
                    holder.bind(
                        it
                    )
                }
            }
        }
    }

    fun submitPokedexMoves(list: List<DMoves>) {
        adapterScope.launch {
            val listOfMoves = mutableListOf<MovesListItems?>()

            list.forEach { dMoves ->
                val items = MovesListItems.MoveItem(dMoves.move)
                listOfMoves.add(items)
            }
            withContext(Dispatchers.Main) {
                submitList(listOfMoves)
            }
        }
    }

    class MovesViewHolder private constructor(
        val binding: ItemMoveBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            move: DMove
        ) {
            binding.move = move
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MovesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMoveBinding.inflate(layoutInflater, parent, false)
                return MovesViewHolder(binding)
            }
        }
    }
}

class MovesListDiffCallback : DiffUtil.ItemCallback<MovesListItems>() {
    override fun areItemsTheSame(
        oldItem: MovesListItems, newItem: MovesListItems
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: MovesListItems, newItem: MovesListItems
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class MovesListItems {
    data class MoveItem(val moves: DMove?) : MovesListItems() {
        override val name = moves?.name ?: ""
    }

    abstract val name: String
}