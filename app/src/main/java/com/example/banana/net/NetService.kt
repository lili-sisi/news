package com.example.banana.net

import com.example.banana.data.BeforeNews
import com.example.banana.data.Comments
import com.example.banana.data.LatestNews
import retrofit2.http.GET
import retrofit2.http.Path

interface NetService{
    companion object{
        val BASE_URL="https://news-at.zhihu.com/api/4/"
    }
    @GET("news/latest")
    suspend fun getLatestNews(): LatestNews
    @GET("news/before/{date}")
    suspend fun getBeforeNews(@Path("date")date: String): BeforeNews
    @GET("story/{id}/short-comments")
    suspend fun getComments(@Path("id") id: Long): Comments
    @GET("story/{id}/long-comments")
    suspend fun getLongComments(@Path("id") id: Long): Comments
}


