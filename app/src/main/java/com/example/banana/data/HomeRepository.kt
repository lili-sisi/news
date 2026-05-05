package com.example.banana.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banana.data.NewsListItem
import com.example.banana.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){
    val isLoading= MutableLiveData(false)
    private val repository= HomeRepository()
    private val _latestNews= MutableLiveData<LatestNews>()
    val latestNews: LiveData<LatestNews>
        get()=_latestNews
    private val _allNews = MutableLiveData<MutableList<NewsListItem>>(mutableListOf())
    val allNews: LiveData<MutableList<NewsListItem>>
        get() = _allNews
    private var currentDate: String?=null
    private val _comment= MutableLiveData<MutableList<Comment>>(mutableListOf())
    val comment: LiveData<MutableList<Comment>>
        get()= _comment
    fun loadLatestNews(){
        viewModelScope.launch {
           val result=repository.fetchLatestNews()
            result.onSuccess { data->
                val newList = mutableListOf<NewsListItem>()
                newList.add(NewsListItem.DataHeader(formatZhiHuDate(data.date)))
                data.stories.forEach { s ->
                    newList.add(
                        NewsListItem.StoryItem(
                        id = s.id.toLong(),
                        title = s.title,
                        hint = s.hint,
                        url = s.url,
                        imageUrl = s.images.firstOrNull() ?: ""
                    ))
                }
                _allNews.postValue(newList)
                _latestNews.value=data
                currentDate=data.date
            }
            result.onFailure { exception ->
                Log.d("网络请求失败", exception.toString())
            }
        }
    }
    fun loadBeforeNews() {
        if (isLoading.value == true) return
        val date = currentDate ?: return
        isLoading.value = true
        viewModelScope.launch {
            val result=repository.fetchBeforeNews(date)
            result.onSuccess { data->
                val currentList = _allNews.value ?: mutableListOf()
                currentList.add(NewsListItem.DataHeader(formatZhiHuDate(data.date)))
                data.stories.forEach { s ->
                    currentList.add(
                        NewsListItem.StoryItem(
                        id = s.id.toLong(),
                        title = s.title,
                        hint = s.hint,
                        url = s.url,
                        imageUrl = s.images.firstOrNull() ?: ""
                    ))
                }
                _allNews.value = currentList
                currentDate= data.date
            }
            isLoading.value = false

        }
    }
     fun loadComment(id: Long){
        viewModelScope.launch {
            repository.fetchComment(id,
                {_comment.value = it},{ })
        }
    }
     fun formatZhiHuDate(date: String): String {
        return try {
            val month = date.substring(4, 6).toInt()
            val day = date.substring(6, 8).toInt()
            "${month}月${day}日"
        } catch (e: Exception) {
            date

        }
    }
}