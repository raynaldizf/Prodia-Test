package com.app.prodiatest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.prodiatest.data.model.request.RecentSearchEntity
import com.app.prodiatest.data.model.response.ResponseGetDetailArticle
import com.app.prodiatest.data.model.response.ResponseGetNewsSite
import com.app.prodiatest.data.model.response.ResultGetArticle
import com.app.prodiatest.data.network.ApiClient
import com.app.prodiatest.data.network.ApiResponse
import com.app.prodiatest.data.room.ArticleDatabase
import com.app.prodiatest.paging.ArticlePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val articleDao = ArticleDatabase.getDatabase(application)!!.articleDao()

    private val _newsSite = MutableLiveData<ApiResponse<ResponseGetNewsSite>>()
    val newsSite: LiveData<ApiResponse<ResponseGetNewsSite>> get() = _newsSite

    private val _detailArticle = MutableLiveData<ApiResponse<ResponseGetDetailArticle>>()
    val detailArticle: LiveData<ApiResponse<ResponseGetDetailArticle>> get() = _detailArticle

    fun getDetailArticle(id: String) {
        _detailArticle.postValue(ApiResponse.Loading())
        ApiClient.instance.getDetailArticle(id).enqueue(object :
            Callback<ResponseGetDetailArticle> {
            override fun onResponse(call: Call<ResponseGetDetailArticle>, response: Response<ResponseGetDetailArticle>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _detailArticle.postValue(ApiResponse.Success(responseBody))
                } else {
                    val errorBody = response.errorBody()?.string()
                    var errorMessage = "Unknown error"
                    if (errorBody != null) {
                        try {
                            val errorJson = JSONObject(errorBody)
                            errorMessage = errorJson.optString("message", "Unknown error")
                        } catch (e: JSONException) {
                            Log.e("JSON Parse Error", "Failed to parse error body", e)
                        }
                    }
                    _detailArticle.postValue(ApiResponse.Error(errorMessage))
                }
            }

            override fun onFailure(call: Call<ResponseGetDetailArticle>, t: Throwable) {
                _detailArticle.postValue(ApiResponse.Error(t.message ?: "Unknown error"))
            }
        })
    }

    fun getNewsSite() {
        _newsSite.postValue(ApiResponse.Loading())
        ApiClient.instance.getNewsSite().enqueue(object :
            Callback<ResponseGetNewsSite> {
            override fun onResponse(call: Call<ResponseGetNewsSite>, response: Response<ResponseGetNewsSite>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _newsSite.postValue(ApiResponse.Success(responseBody))
                } else {
                    val errorBody = response.errorBody()?.string()
                    var errorMessage = "Unknown error"
                    if (errorBody != null) {
                        try {
                            val errorJson = JSONObject(errorBody)
                            errorMessage = errorJson.optString("message", "Unknown error")
                        } catch (e: JSONException) {
                            Log.e("JSON Parse Error", "Failed to parse error body", e)
                        }
                    }
                    _newsSite.postValue(ApiResponse.Error(errorMessage))
                }
            }

            override fun onFailure(call: Call<ResponseGetNewsSite>, t: Throwable) {
                _newsSite.postValue(ApiResponse.Error(t.message ?: "Unknown error"))
            }
        })
    }

    fun getArticles(query: String? = null, newsSite: String? = null): Flow<PagingData<ResultGetArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingSource(ApiClient.instance, query, newsSite) }
        ).flow.cachedIn(viewModelScope)
    }

    fun saveRecentSearch(query: String) {
        viewModelScope.launch {
            val recentSearchEntity = RecentSearchEntity(query = query)
            articleDao.insertRecent(recentSearchEntity)
        }
    }

    fun getAllRecentSearches(): LiveData<List<RecentSearchEntity>> {
        return articleDao.getAllRecentSearches()
    }
}
