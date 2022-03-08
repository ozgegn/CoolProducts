package com.ozgegn.coolblueproducts.util

import com.ozgegn.coolblueproducts.data.remote.model.Product
import com.ozgegn.coolblueproducts.domain.model.ProductBO

fun mockProductBO() = ProductBO(
    productId = 1000,
    productName = "New Item",
    reviewCount = 10,
    reviewAverage = 1.0,
    USPs = listOf("Nice"),
    salesPriceIncVat = 10.0,
    productImage = "",
    nextDayDelivery = false
)

fun mockProductBOList() = listOf(mockProductBO())

fun mockProduct() = Product(
    productId = 1000,
    productName = "New Item",
    reviewInformation = null,
    USPs = listOf("Nice"),
    availabilityState = null,
    salesPriceIncVat = 100.0,
    productImage = "",
    nextDayDelivery = false
)

fun mockProductList() = listOf(mockProduct())