package com.ozgegn.coolblueproducts.di

import com.ozgegn.coolblueproducts.data.remote.model.mapper.ProductMapper
import com.ozgegn.coolblueproducts.domain.usecase.SearchProductsUseCase
import com.ozgegn.coolblueproducts.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideMainViewModel(
        searchProductsUseCase: SearchProductsUseCase,
        productMapper: ProductMapper
    ): MainViewModel = MainViewModel(searchProductsUseCase, productMapper)

}