package com.example.banana.data

data class BeforeNews (
    val date: String,
    val stories: List<BeforeStory>,
)
data class BeforeStory(
    val ga_prefix: String,
    val hint: String,
    val id: Long,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String
)


