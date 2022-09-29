package com.sandoval.mypokedex.ui.pokedex_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.mypokedex.databinding.ItemTypeBinding
import com.sandoval.mypokedex.domain.models.pokedex_detail.DType
import com.sandoval.mypokedex.domain.models.pokedex_detail.DTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypesAdapter :
    ListAdapter<TypesListItems, RecyclerView.ViewHolder>(TypesListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TypesListItems.TypeItem -> 0
            else -> throw ClassCastException("Unknown Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TypesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is TypesViewHolder -> {
                val type = item as TypesListItems.TypeItem
                type.types?.let {
                    holder.bind(
                        it
                    )
                }
            }
        }
    }

    fun submitPokedexTypes(list: List<DTypes>) {
        adapterScope.launch {
            val listOfTypes = mutableListOf<TypesListItems?>()

            list.forEach { dTypes ->
                val items = TypesListItems.TypeItem(dTypes.type)
                listOfTypes.add(items)
            }
            withContext(Dispatchers.Main) {
                submitList(listOfTypes)
            }
        }
    }

    class TypesViewHolder private constructor(
        val binding: ItemTypeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            type: DType
        ) {
            binding.type = type
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TypesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTypeBinding.inflate(layoutInflater, parent, false)
                return TypesViewHolder(binding)
            }
        }
    }

}

class TypesListDiffCallback : DiffUtil.ItemCallback<TypesListItems>() {
    override fun areItemsTheSame(
        oldItem: TypesListItems, newItem: TypesListItems
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: TypesListItems, newItem: TypesListItems
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class TypesListItems {
    data class TypeItem(val types: DType?) : TypesListItems() {
        override val name = types?.name ?: ""
    }

    abstract val name: String
}