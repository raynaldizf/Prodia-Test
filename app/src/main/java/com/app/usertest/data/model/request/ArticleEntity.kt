package com.app.prodiatest.data.model.request

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val summary: String,
    val imageUrl: String,
    val publishedAt: String,
    val news_site: String
)