package com.github.wisemann64.snapdishapp.ui.items


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.wisemann64.snapdishapp.databinding.ItemMainIngredientSelectorBinding

class ListMainIngredientAdapter: ListAdapter<String, ListMainIngredientAdapter.ListMainIngredientViewHolder>(
    DIFF_CALLBACK) {

    class ListMainIngredientViewHolder(private val binding: ItemMainIngredientSelectorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(main: String) {
            binding.text.text = main
        }
    }

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMainIngredientViewHolder {
        val binding = ItemMainIngredientSelectorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListMainIngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMainIngredientViewHolder, position: Int) {
        val value = getItem(position)
        holder.bind(value)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(position,value)
        }
    }

    private lateinit var onClickListener: OnClickListenerString

    fun setOnClickListener(listener: OnClickListenerString) {
        this.onClickListener = listener
    }
}