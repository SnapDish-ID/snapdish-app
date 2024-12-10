package com.github.wisemann64.snapdishapp.ui.items

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.data.DataIngredient
import com.github.wisemann64.snapdishapp.databinding.ItemIngredientBinding
import com.github.wisemann64.snapdishapp.ui.list.ListFragment
import com.github.wisemann64.snapdishapp.ui.list.ListViewModel

class ListIngredientsAdapter(private val fragment: ListFragment, private val viewModel: ListViewModel): ListAdapter<DataIngredient, ListIngredientsAdapter.ListIngredientsViewHolder>(
    DIFF_CALLBACK) {

    class ListIngredientsViewHolder(private val binding: ItemIngredientBinding, private val fragment: ListFragment, private val viewModel: ListViewModel): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: DataIngredient, position: Int) {
            binding.ingredientText.setText(value.value)

            binding.clearButton.setOnClickListener {
//                Toast.makeText(fragment.requireContext(), "Hi! $position", Toast.LENGTH_SHORT).show()
                viewModel.removeItem(position)
            }

            binding.ingredientText.addTextChangedListener {
                viewModel.setRecipe(position, it.toString())
            }

            binding.ingredientText.hint = "Bahan ke-${position+1}"
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
        return ListIngredientsViewHolder(binding,fragment, viewModel)
    }

    override fun onBindViewHolder(holder: ListIngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient, position)
//        holder.binding
    }

}