package com.github.wisemann64.snapdishapp.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.databinding.ItemRecipeIngredientBinding

class ListRecipeIngredientsAdapter: ListAdapter<String, ListRecipeIngredientsAdapter.ListRecipeIngredientsViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ListRecipeIngredientsViewHolder(private val binding: ItemRecipeIngredientBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: String) {
            binding.textIngredient.text = value
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListRecipeIngredientsViewHolder {
        val binding = ItemRecipeIngredientBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListRecipeIngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRecipeIngredientsViewHolder, position: Int) {
        val value = getItem(position)
        holder.bind(value)
    }

}