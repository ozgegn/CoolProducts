package com.ozgegn.coolblueproducts.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPaginator(
    recyclerView: RecyclerView,
    private val isLoading: () -> Boolean,
    private val loadMore: (Int) -> Unit,
    private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    var threshold: Int = 10
    var currentPage: Int = 0

    var endWithAuto = false

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView.layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val lastVisibleItemPosition = when (it) {
                is LinearLayoutManager -> it.findLastVisibleItemPosition()
                else -> return
            }

            if (onLast() || isLoading()) return

            if (endWithAuto) {
                if (visibleItemCount + lastVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + lastVisibleItemPosition + threshold) >= totalItemCount) {
                loadMore(++currentPage)
            }
        }
    }

    private fun findLastVisibleItemPosition(lastVisibleItems: IntArray): Int {
        return lastVisibleItems.maxOfOrNull { it } ?: lastVisibleItems[0]
    }

    fun resetCurrentPage() {
        this.currentPage = 0
    }
}