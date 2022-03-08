package com.ozgegn.coolblueproducts.data.repository

import com.ozgegn.coolblueproducts.data.remote.ApiService
import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import com.ozgegn.coolblueproducts.domain.repository.BaseApiResponse
import com.ozgegn.coolblueproducts.domain.repository.SearchRepository
import com.ozgegn.coolblueproducts.util.BaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SearchRepository, BaseApiResponse() {
    override suspend fun search(page: Int, query: String): Flow<BaseResult<SearchResponse>> =
        flow {
            emit(safeApiCall {
                api.search(query, page)
            })
        }.flowOn(ioDispatcher)
}