package com.example.dubnikinfo.presentation.ui.actualities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dubnikinfo.data.local.news.NewsLine
import com.example.dubnikinfo.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _news = MutableStateFlow<List<NewsLine>>(emptyList())
    val news: StateFlow<List<NewsLine>> = _news

    init {
        viewModelScope.launch {
            _news.value = repository.getCurrentActualities()
        }
    }
}