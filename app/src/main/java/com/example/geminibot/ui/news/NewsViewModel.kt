package com.example.geminibot.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geminibot.data.NewsRepository
import com.example.geminibot.data.api.RetrofitInstance
import com.example.geminibot.data.model.NewsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository(RetrofitInstance.api)
    private val _uiState = MutableStateFlow<List<NewsItem?>>(emptyList())
    val uiState = _uiState.asStateFlow()


    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            val result = repository.fetchNews("tCXpH755bj5kmyi7mCNZzXVlEBFG80Aa")
            result.onSuccess {
                it.results?.let { itemList ->
                    _uiState.value = itemList
                }
            }.onFailure {
                Log.d("TAG", "loadNews: $it")
            }
        }
    }
}