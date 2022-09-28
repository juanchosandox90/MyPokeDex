package com.sandoval.mypokedex.ui.pokedex_detail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.mypokedex.databinding.ItemAbilityBinding
import com.sandoval.mypokedex.domain.models.pokedex_detail.DAbilites
import com.sandoval.mypokedex.domain.models.pokedex_detail.DAbility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AbilitiesAdapter :
    ListAdapter<AbilitiesListItems, RecyclerView.ViewHolder>(AbilitiesListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AbilitiesListItems.AbilityItem -> 0
            else -> throw ClassCastException("Unknown Type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> AbilitiesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is AbilitiesViewHolder -> {
                val ability = item as AbilitiesListItems.AbilityItem
                ability.abilities?.let {
                    holder.bind(
                        it
                    )
                }
            }
        }
    }

    fun submitPokedexAbilities(list: List<DAbilites>) {
        adapterScope.launch {
            val listOfAbilities = mutableListOf<AbilitiesListItems?>()

            list.forEach { dAbilities ->
                val items = AbilitiesListItems.AbilityItem(dAbilities.ability)
                listOfAbilities.add(items)
            }
            withContext(Dispatchers.Main) {
                submitList(listOfAbilities)
            }
        }
    }

    class AbilitiesViewHolder private constructor(
        val binding: ItemAbilityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            ability: DAbility
        ) {
            binding.ability = ability
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AbilitiesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAbilityBinding.inflate(layoutInflater, parent, false)
                return AbilitiesViewHolder(binding)
            }
        }
    }
}

class AbilitiesListDiffCallback : DiffUtil.ItemCallback<AbilitiesListItems>() {
    override fun areItemsTheSame(
        oldItem: AbilitiesListItems, newItem: AbilitiesListItems
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: AbilitiesListItems, newItem: AbilitiesListItems
    ): Boolean {
        return oldItem == newItem
    }
}

sealed class AbilitiesListItems {
    data class AbilityItem(val abilities: DAbility?) : AbilitiesListItems() {
        override val name = abilities?.name ?: ""
    }

    abstract val name: String
}