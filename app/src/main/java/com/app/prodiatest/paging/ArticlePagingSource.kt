package com.app.prodiatest.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.prodiatest.data.model.response.ResultGetArticle
import com.app.prodiatest.data.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class ArticlePagingSource(
    private val apiService: ApiService,
    private val query: String? = null,
    private val newsSite: String? = null
) : PagingSource<Int, ResultGetArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultGetArticle> {
        val position = params.key ?: 0

        return try {
            val response = apiService.getArticles(params.loadSize, position, query,newsSite)
            val articles = response.resultGetArticles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + params.loadSize
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultGetArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
