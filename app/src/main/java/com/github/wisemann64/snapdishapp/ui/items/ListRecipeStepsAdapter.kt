package com.github.wisemann64.snapdishapp.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.databinding.ItemRecipeIngredientBinding
import com.github.wisemann64.snapdishapp.databinding.ItemRecipeStepsBinding

class ListRecipeStepsAdapter: ListAdapter<String,ListRecipeStepsAdapter.ListRecipeStepsViewHolder>(
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

    class ListRecipeStepsViewHolder(private val binding: ItemRecipeStepsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: String) {
            binding.textStep.text = value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecipeStepsViewHolder {
        val binding = ItemRecipeStepsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListRecipeStepsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRecipeStepsViewHolder, position: Int) {
        val value = getItem(position)
        holder.bind(value)
    }
}