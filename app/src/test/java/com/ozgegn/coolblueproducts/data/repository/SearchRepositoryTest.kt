package com.ozgegn.coolblueproducts.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.whenever
import com.ozgegn.coolblueproducts.data.remote.ApiService
import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import com.ozgegn.coolblueproducts.domain.repository.SearchRepository
import com.ozgegn.coolblueproducts.util.MainCoroutineRule
import com.ozgegn.coolblueproducts.util.mockProductList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@ExperimentalTime
@ExperimentalCoroutinesApi
class SearchRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineRule()

    private lateinit var searchRepository: SearchRepository
    private val apiService: ApiService = Mockito.mock(ApiService::class.java)

    @Before
    fun setUp() {
        searchRepository = SearchRepositoryImpl(apiService)
    }

    @Test
    fun `fetch productlist from network return success`() = runBlocking {
        val mockData = SearchResponse(mockProductList(), 1, 10, 10, 2)
        whenever(apiService.search("", 0)).thenReturn(Response.success(mockData))
        searchRepository.search(0, "").test(3.toDuration(DurationUnit.SECONDS)) {
            val product = awaitItem().data?.products?.get(0)
            requireNotNull(product)
            assertThat(product.productId, `is`(1000))
            assertThat(product.productName, `is`("New Item"))
            awaitComplete()
        }
    }
}