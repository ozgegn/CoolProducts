package com.ozgegn.coolblueproducts.domain.model

data class ProductBO(
    val productId: Long,
    val productName: String,
    val reviewAverage: Double = 0.0,
    val reviewCount: Int = 0,
    val USPs: List<String>,
    val salesPriceIncVat: Double = 0.0,
    val productImage: String?,
    val nextDayDelivery: Boolean = false
)