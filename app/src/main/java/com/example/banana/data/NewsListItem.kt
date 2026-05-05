package com.example.banana.data

sealed class NewsListItem {
    data class DataHeader(val dateText: String) : NewsListItem()

    data class StoryItem(
        val id: Long, val title: String, val hint: String, val imageUrl: String, val url: String
    ) :  NewsListItem()

}