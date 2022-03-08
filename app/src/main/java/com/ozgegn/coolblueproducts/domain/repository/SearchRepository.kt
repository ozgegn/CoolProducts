package com.ozgegn.coolblueproducts.domain.repository

import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import com.ozgegn.coolblueproducts.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(page: Int, query: String): Flow<BaseResult<SearchResponse>>
}