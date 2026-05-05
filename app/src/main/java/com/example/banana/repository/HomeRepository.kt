package com.example.banana.repository

import android.util.Log
import com.example.banana.data.BeforeNews
import com.example.banana.data.Comment
import com.example.banana.data.LatestNews
import com.example.banana.net.HttpUtil

class HomeRepository {
    suspend fun fetchLatestNews(): Result<LatestNews> = try {
        val response= HttpUtil.netService.getLatestNews()
        Result.success(response)
    }catch (e: Exception){
        Log.d("1",e.stackTraceToString())
        Result.failure(e)
    }
    suspend fun fetchBeforeNews(date:String): Result<BeforeNews> = try{
        val response= HttpUtil.netService.getBeforeNews(date)
        Result.success(response)
    }catch(e:Exception){
        Log.d("1",e.stackTraceToString())
        Result.failure(e)
    }
    suspend fun fetchComment(id: Long, isSuccess:(MutableList<Comment>)->Unit, isFailure:(String)->Unit ){
      try{ val commentLong= HttpUtil.netService.getLongComments(id)
        val commentShort= HttpUtil.netService.getComments(id)
        val comments: MutableList<Comment> = mutableListOf()
        comments.addAll(commentShort.comments)
        comments.addAll(commentLong.comments)
        isSuccess(comments)
      }catch (e: Exception){
          Log.e("评论错误", "加载失败：${e.message}")
          isFailure(e.message.toString())
      }
    }

}