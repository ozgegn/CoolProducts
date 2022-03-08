package com.ozgegn.coolblueproducts.domain.usecase

import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import com.ozgegn.coolblueproducts.domain.repository.SearchRepository
import com.ozgegn.coolblueproducts.util.BaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class SearchProductsUseCase(
    private val searchRepository: SearchRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<SearchProductsUseCase.Param, BaseResult<SearchResponse>>(ioDispatcher) {

    override suspend fun getExecutable(params: Param): Flow<BaseResult<SearchResponse>> {
        return searchRepository.search(params.page, params.query)
    }

    data class Param(
        val page: Int,
        val query: String
    )

}