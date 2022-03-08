package com.ozgegn.coolblueproducts.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ozgegn.coolblueproducts.domain.model.ProductBO

class SearchProductAdapter : ListAdapter<ProductBO, SearchProductViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        return SearchProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.onBindView(getItem(position))
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<ProductBO>() {
        override fun areItemsTheSame(oldItem: ProductBO, newItem: ProductBO): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductBO, newItem: ProductBO): Boolean {
            return oldItem == newItem
        }

    }
}