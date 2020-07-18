package com.stegnerd.jeopardy.ui.categoryselect

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stegnerd.jeopardy.data.model.Category
import com.stegnerd.jeopardy.util.Result
import com.stegnerd.jeopardy.util.Status

/**
 * [BindingAdapter] for the [Category] list
 *
 * Updates the [ListAdapter] with values when the result was a success.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: Result<List<Category>>?) {
    if(items?.status == Status.SUCCESS){
        (listView.adapter as CategorySelectAdapter).submitList(items?.data)
    }
}