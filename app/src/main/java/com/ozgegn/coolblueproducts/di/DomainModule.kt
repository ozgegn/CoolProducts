package com.ozgegn.coolblueproducts.di

import com.ozgegn.coolblueproducts.data.remote.ApiService
import com.ozgegn.coolblueproducts.data.repository.SearchRepositoryImpl
import com.ozgegn.coolblueproducts.domain.repository.SearchRepository
import com.ozgegn.coolblueproducts.domain.usecase.SearchProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(api: ApiService): SearchRepository = SearchRepositoryImpl(api)

    @Provides
    @ViewModelScoped
    fun provideSearchProductsUseCase(searchRepository: SearchRepository): SearchProductsUseCase = SearchProductsUseCase(searchRepository)
}