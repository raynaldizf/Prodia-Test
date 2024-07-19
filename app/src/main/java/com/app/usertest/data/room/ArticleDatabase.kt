package com.app.prodiatest.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.prodiatest.data.model.request.ArticleEntity
import com.app.prodiatest.data.model.request.RecentSearchEntity

@Database(entities = [ArticleEntity::class, RecentSearchEntity::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
