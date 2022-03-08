package com.ozgegn.coolblueproducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgegn.coolblueproducts.data.remote.model.mapper.ProductMapper
import com.ozgegn.coolblueproducts.domain.model.ProductBO
import com.ozgegn.coolblueproducts.domain.usecase.SearchProductsUseCase
import com.ozgegn.coolblueproducts.util.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val productMapper: ProductMapper
) : ViewModel() {

    private val state = MutableStateFlow<MainScreenState>(MainScreenState.Init)
    val mState: StateFlow<MainScreenState> get() = state

    private val products = MutableStateFlow<List<ProductBO>>(mutableListOf())
    val mProducts: StateFlow<List<ProductBO>> get() = products

    private val fetchingIndex: MutableStateFlow<Int> = MutableStateFlow(1)
    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    private var _isLoading = false
    val isLoading = _isLoading

    init {
        searchProducts()
    }

    fun searchProducts() {
        viewModelScope.launch {
            searchQuery.filter { query ->
                return@filter query.isNotEmpty()
            }
                .flatMapLatest { query ->
                    searchProductsUseCase.getExecutable(
                        SearchProductsUseCase.Param(
                            fetchingIndex.value,
                            query
                        )
                    )
                }.onStart {
                    _isLoading = true
                    setLoading()
                }
                .catch { error ->
                    _isLoading = false
                    hideLoading()
                    showToast(error.message.orEmpty())
                }
                .collect { result ->
                    _isLoading = false
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            products.value =
                                result.data?.products?.map { productMapper.mapToDomain(it) }
                                    ?: listOf()
                        }
                        is BaseResult.Error -> {
                            showToast(result.message.orEmpty())
                        }
                    }
                }

        }
    }

    private fun setLoading() {
        state.value = MainScreenState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = MainScreenState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = MainScreenState.ShowToast(message)
    }

    fun fetchNextProductList() {
        if (!_isLoading) {
            fetchingIndex.value++
            getNextPage()
        }
    }

    private fun getNextPage() {
        viewModelScope.launch {
            fetchingIndex
                .flatMapLatest { index ->
                searchProductsUseCase.getExecutable(
                    SearchProductsUseCase.Param(
                        fetchingIndex.value,
                        searchQuery.value
                    )
                )
            }.onStart {
                    _isLoading = true
                    setLoading()
                }
                .catch { error ->
                    _isLoading = false
                    hideLoading()
                    showToast(error.message.orEmpty())
                }
                .collect { result ->
                    _isLoading = false
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                           val latestProducts =
                                result.data?.products?.map { productMapper.mapToDomain(it) }
                                    ?: listOf()
                            addProducts(latestProducts)
                        }
                        is BaseResult.Error -> {
                            showToast(result.message.orEmpty())
                        }
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    private fun addProducts(pagedProducts: List<ProductBO>) {
        val currentProductList = ArrayList(products.value)
        currentProductList.addAll(pagedProducts)
        products.value = currentProductList
    }
}

sealed class MainScreenState {
    object Init : MainScreenState()
    data class IsLoading(val isLoading: Boolean) : MainScreenState()
    data class ShowToast(val message: String) : MainScreenState()
}