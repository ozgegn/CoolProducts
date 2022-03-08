package com.ozgegn.coolblueproducts.di

import com.ozgegn.coolblueproducts.data.remote.model.mapper.ProductMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideProductMapper(): ProductMapper = ProductMapper()

}