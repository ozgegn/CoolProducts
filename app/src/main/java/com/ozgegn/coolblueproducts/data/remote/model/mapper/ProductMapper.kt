package com.ozgegn.coolblueproducts.data.remote.model.mapper

import com.ozgegn.coolblueproducts.data.remote.model.Product
import com.ozgegn.coolblueproducts.domain.model.ProductBO

class ProductMapper : ApiMapper<Product, ProductBO> {
    override fun mapToDomain(apiEntity: Product?): ProductBO {
        return ProductBO(
            productId = apiEntity?.productId ?: 0,
            productName = apiEntity?.productName ?: "",
            reviewAverage = apiEntity?.reviewInformation?.reviewSummary?.reviewAverage ?: 0.0,
            reviewCount = apiEntity?.reviewInformation?.reviewSummary?.reviewCount ?: 0,
            USPs = apiEntity?.USPs ?: emptyList(),
            salesPriceIncVat = apiEntity?.salesPriceIncVat ?: 0.0,
            productImage = apiEntity?.productImage,
            nextDayDelivery = apiEntity?.nextDayDelivery ?: false
        )
    }
}