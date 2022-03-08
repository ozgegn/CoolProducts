package com.ozgegn.coolblueproducts.data.remote

import com.ozgegn.coolblueproducts.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ApiServiceTest: ApiAbstract<ApiService>() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = createService(ApiService::class.java)
    }

    @Test
    fun fetchProductList() = runBlocking {
        enqueueResponse("/productsResponse.json")
        val response = apiService.search("", 0)
        val responseBody = requireNotNull(response.body())
        mockWebServer.takeRequest()

        assertThat(responseBody.products?.size, `is`(2))
        assertThat(responseBody.products?.get(0)?.productId, `is`(785359))
    }
}