package com.stegnerd.jeopardy.ui.categoryselect

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stegnerd.jeopardy.R
import com.stegnerd.jeopardy.data.model.Category

/**
 * [RecyclerView.Adapter] that can display a [Category].
 */
class CategorySelectAdapter(private val values: List<Category>) : RecyclerView.Adapter<CategorySelectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_select_item_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.categoryView.text = item.title
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryView: TextView = view.findViewById(R.id.CategoryName)

        override fun toString(): String {
            return super.toString() + " '" + categoryView.text + "'"
        }
    }
}