package com.ozgegn.coolblueproducts.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ozgegn.coolblueproducts.R
import com.ozgegn.coolblueproducts.presentation.MainViewModel
import com.ozgegn.coolblueproducts.util.RecyclerViewPaginator

@BindingAdapter("loadUrl")
fun bindProductImage(imageView: ImageView?, url: String?) {
    imageView?.let {
        Glide.with(it.context).load(url).into(it)
    }
}

@BindingAdapter("averageVoteValue")
fun bindVoteAverageText(textView: TextView?, value: Double) {
    textView?.context?.let {
        textView.text =
            String.format(it.resources.getString(R.string.vote_average_unit_format), value)
    }
}

@BindingAdapter("totalAmountValue")
fun bindTotalAmount(textView: TextView?, value: Double) {
    textView?.context?.let {
        textView.text =
            String.format(it.resources.getString(R.string.total_amount_unit_format), value)
    }
}

@BindingAdapter("paginationProductList")
fun bindPaginationProductList(view: RecyclerView?, viewModel: MainViewModel) {
    view?.let {
        RecyclerViewPaginator(
            recyclerView = view,
            isLoading = { viewModel.isLoading },
            loadMore = { viewModel.fetchNextProductList() },
            onLast = { false }
        ).run {
            threshold = 8
        }
    }
}