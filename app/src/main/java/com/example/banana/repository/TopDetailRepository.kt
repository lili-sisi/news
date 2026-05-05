package com.example.banana.repository

import com.example.banana.data.LatestNews
import com.example.banana.net.HttpUtil

 class TopDetailRepository{
        suspend fun toGetLatestNews(isSuccess:(LatestNews)-> Unit, isFailure:(String)-> Unit){
            try {
                HttpUtil.netService.getLatestNews().run (isSuccess)
            }catch (e: Exception){
                isFailure("网络请求失败")
            }
        }

}