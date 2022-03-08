package com.ozgegn.coolblueproducts.data.remote

import com.ozgegn.coolblueproducts.data.remote.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun search(@Query("query") query: String, @Query("page") page: Int): Response<SearchResponse>

}