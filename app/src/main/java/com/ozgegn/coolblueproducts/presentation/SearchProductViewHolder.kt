package com.ozgegn.coolblueproducts.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozgegn.coolblueproducts.R
import com.ozgegn.coolblueproducts.databinding.RowProductBinding
import com.ozgegn.coolblueproducts.domain.model.ProductBO

class SearchProductViewHolder(private val binding: RowProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindView(productBO: ProductBO) {
        with(binding) {
            product = productBO
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): SearchProductViewHolder {
            val binding = DataBindingUtil.inflate<RowProductBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_product,
                parent,
                false
            )
            return SearchProductViewHolder(binding)
        }
    }

}