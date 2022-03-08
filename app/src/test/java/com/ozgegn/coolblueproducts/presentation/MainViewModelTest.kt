package com.ozgegn.coolblueproducts.presentation

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.whenever
import com.ozgegn.coolblueproducts.data.remote.ApiService
import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import com.ozgegn.coolblueproducts.data.remote.model.mapper.ProductMapper
import com.ozgegn.coolblueproducts.data.repository.SearchRepositoryImpl
import com.ozgegn.coolblueproducts.domain.repository.SearchRepository
import com.ozgegn.coolblueproducts.domain.usecase.SearchProductsUseCase
import com.ozgegn.coolblueproducts.util.MainCoroutineRule
import com.ozgegn.coolblueproducts.util.mockProductList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalCoroutinesApi
@ExperimentalTime
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private lateinit var searchRepository: SearchRepository
    private lateinit var searchProductsUseCase: SearchProductsUseCase
    private lateinit var productMapper: ProductMapper
    private val apiService = Mockito.mock(ApiService::class.java)

    @get:Rule
    var coroutinesRule = MainCoroutineRule()

    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(apiService)
        searchProductsUseCase = SearchProductsUseCase(searchRepository)
        productMapper = ProductMapper()
        viewModel = MainViewModel(searchProductsUseCase, productMapper)
    }

    @Test
    fun `fetch product list with query return success data`() = runBlocking {
        val mockData = SearchResponse(mockProductList(), 1, 10, 10, 2)
        whenever(
            apiService.search("", 0)
        ).thenReturn(Response.success(mockData))

        searchProductsUseCase.getExecutable(SearchProductsUseCase.Param(0, ""))
            .test(3.toDuration(DurationUnit.SECONDS)) {
                val product = awaitItem()
                requireNotNull(product.data?.products)
                Assert.assertEquals(1, product.data?.products?.size)
            }
    }
}