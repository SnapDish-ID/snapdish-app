package com.github.wisemann64.snapdishapp.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.data.DataRecipe
import com.github.wisemann64.snapdishapp.databinding.ItemRecipeBinding

class ListRecipeAdapter: ListAdapter<DataRecipe, ListRecipeAdapter.ListDataRecipeViewHolder>(
    DIFF_CALLBACK) {

    class ListDataRecipeViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataRecipe) {
            binding.recipeName.text = recipe.title
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataRecipe>() {
            override fun areItemsTheSame(oldItem: DataRecipe, newItem: DataRecipe): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataRecipe, newItem: DataRecipe): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataRecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListDataRecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListDataRecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position,recipe)
        }
    }

    private lateinit var onClickListener: OnClickListener

    fun setOnClickListener(listener: OnClickListener) {
        this.onClickListener = listener
    }

}