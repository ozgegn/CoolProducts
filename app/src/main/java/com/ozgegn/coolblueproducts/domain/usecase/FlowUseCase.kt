package com.ozgegn.coolblueproducts.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
abstract class FlowUseCase<in P, T> constructor(
    private val dispatcher: CoroutineDispatcher
) {
    abstract suspend fun getExecutable(params: P): Flow<T>

    suspend operator fun invoke(params: P): Flow<T> {
        return withContext(dispatcher) {
            getExecutable(params)
        }
    }
}