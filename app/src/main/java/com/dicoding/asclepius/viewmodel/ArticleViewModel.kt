package com.dicoding.asclepius.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.ResultState
import com.dicoding.asclepius.data.network.response.ArticlesItem
import com.dicoding.asclepius.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val _articles = MutableLiveData<ResultState<List<ArticlesItem>>>()
    val articles: LiveData<ResultState<List<ArticlesItem>>> = _articles

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            _articles.value = ResultState.Loading
            try {
                val response = ApiConfig.getApiService().getArticles(QUERY, CATEGORY, LANGUAGE)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles?.filterNotNull()
                    if (!articles.isNullOrEmpty()) {
                        _articles.value = ResultState.Success(articles)
                    } else {
                        _articles.value = ResultState.Error("No articles found")
                    }
                } else {
                    _articles.value = ResultState.Error(response.message())
                }
            } catch (e: Exception) {
                _articles.value = ResultState.Error(e.message ?: "Unknown Error")
                Log.e(TAG, "getArticles: ${e.message}", e)
            }
        }
    }

    companion object{
        private const val TAG = "ArticleViewModel"
        private const val QUERY = "cancer"
        private const val CATEGORY = "health"
        private const val LANGUAGE = "en"
    }
}