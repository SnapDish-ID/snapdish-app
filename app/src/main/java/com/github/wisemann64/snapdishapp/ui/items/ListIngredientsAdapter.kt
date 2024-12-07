package com.github.wisemann64.snapdishapp.ui.items

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.data.DataIngredient
import com.github.wisemann64.snapdishapp.databinding.ItemIngredientBinding

class ListIngredientsAdapter: ListAdapter<DataIngredient, ListIngredientsAdapter.ListIngredientsViewHolder>(
    DIFF_CALLBACK) {

    class ListIngredientsViewHolder(private val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: DataIngredient) {
            binding.ingredientText.setText(value.value)
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataIngredient>() {
            override fun areItemsTheSame(oldItem: DataIngredient, newItem: DataIngredient): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataIngredient, newItem: DataIngredient): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListIngredientsViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListIngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListIngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
//        holder.binding
    }

}