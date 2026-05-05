package com.example.banana.data

data class Comments(
    val comments: List<Comment>
)

data class Comment(
    val author: String,
    val avatar: String,
    val content: String,
    val id: Int,
    val likes: Int,
    val reply_to: ReplyTo,
    val time: Int
)

data class ReplyTo(
    val author: String,
    val content: String,
    val id: Int,
    val status: Int
)