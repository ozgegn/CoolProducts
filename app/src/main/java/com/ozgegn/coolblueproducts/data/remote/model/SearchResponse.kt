package com.ozgegn.coolblueproducts.data.remote.model

data class SearchResponse(
    val products: List<Product>?,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int,
)

data class Product(
    val productId: Long?,
    val productName: String?,
    val reviewInformation: ReviewInformation?,
    val USPs: List<String>?,
    val availabilityState: Int?,
    val salesPriceIncVat: Double?,
    val productImage: String?,
    val nextDayDelivery: Boolean?
)

data class ReviewInformation(
    val reviewSummary: ReviewSummary?
)

data class ReviewSummary(
    val reviewAverage: Double?,
    val reviewCount: Int?
)