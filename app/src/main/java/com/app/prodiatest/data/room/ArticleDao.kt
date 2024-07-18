package com.app.prodiatest.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.prodiatest.data.model.request.ArticleEntity
import com.app.prodiatest.data.model.request.RecentSearchEntity

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Insert
    suspend fun insertRecent(recentSearchEntity: RecentSearchEntity)

    @Query("SELECT * FROM recent_searches ORDER BY id DESC")
    fun getAllRecentSearches(): LiveData<List<RecentSearchEntity>>
}
