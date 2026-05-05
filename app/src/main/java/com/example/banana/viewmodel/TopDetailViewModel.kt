package com.example.banana.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banana.data.TopStory
import com.example.banana.repository.TopDetailRepository
import kotlinx.coroutines.launch

class TopDetailViewModel: ViewModel() {
    private val repository= TopDetailRepository()
    private val _topList: MutableLiveData<List<TopStory>> =MutableLiveData()
    val topList: LiveData<List<TopStory>> = _topList
    fun getTopMes(){
        viewModelScope.launch {
            repository.toGetLatestNews(
                isSuccess = {news->
                    _topList.value = news.top_stories
                },
                isFailure = {}
            )
        }
    }


}