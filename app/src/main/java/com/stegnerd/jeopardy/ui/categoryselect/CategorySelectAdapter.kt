package com.stegnerd.jeopardy.ui.categoryselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.databinding.CategorySelectItemFragmentBinding

/**
 * [RecyclerView.Adapter] that can display a [Category].
 *
 * Has a reference to view model for issuing actions
 */
class CategorySelectAdapter(private val viewModel: CategorySelectViewModel) : ListAdapter<Category, CategorySelectAdapter.ViewHolder>(CategorySelectDiffCallback()) {

    // Called on instantiation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // Called ehn binding to the ui
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        // attaches the data to the viewmodel and executes it.
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(val binding: CategorySelectItemFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        // this binds the viewmodel to the data
        fun bind(viewModel: CategorySelectViewModel, item: Category) {
            binding.category = item

            // notifies the recycler view to rerender because data may have changed
            binding.executePendingBindings()
        }

        companion object {
            // viewholder is the container for the view
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = CategorySelectItemFragmentBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Used to detect differences in the RecyclerView and trigger updates if necessary
 */
class CategorySelectDiffCallback: DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}