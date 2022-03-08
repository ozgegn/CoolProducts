package com.ozgegn.coolblueproducts.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.ozgegn.coolblueproducts.R
import com.ozgegn.coolblueproducts.databinding.ActivityMainBinding
import com.ozgegn.coolblueproducts.domain.model.ProductBO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainViewModel = viewModel
        setupRecyclerView()
        setupSearchView()
        observeState()
        observeProducts()
    }

    private fun observeState() {
        viewModel.mState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleScreenState(state)
            }.launchIn(lifecycleScope)
    }

    private fun observeProducts() {
        viewModel.mProducts.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { products ->
                handleProducts(products)
            }.launchIn(lifecycleScope)
    }

    private fun handleScreenState(state: MainScreenState) {
        when (state) {
            is MainScreenState.IsLoading -> {

            }
            is MainScreenState.ShowToast -> {
                Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleProducts(products: List<ProductBO>) {
        binding.searchResultList.adapter?.let {
            if (it is SearchProductAdapter) {
                it.submitList(products)
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.searchResultList) {
            adapter = SearchProductAdapter()
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no-op
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no-op
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    viewModel.onSearchQueryChanged(s.toString())
                }
            }

        })
    }
}