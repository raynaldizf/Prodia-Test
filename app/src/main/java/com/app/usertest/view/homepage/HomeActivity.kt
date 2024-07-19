package com.app.prodiatest.view.homepage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.prodiatest.databinding.ActivityHomeBinding
import com.app.prodiatest.view.homepage.adapter.HomeAdapter
import com.app.prodiatest.view.homepage.adapter.LoadingStateAdapter
import com.app.prodiatest.view.recentpage.RecentPageActivity
import com.app.prodiatest.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: ArticleViewModel by viewModels()
    private val adapter = HomeAdapter()
    private var hasSavedSearch: Boolean = false
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvArtikel.requestFocus()
        binding.rvArtikel.layoutManager = LinearLayoutManager(this)
        binding.rvArtikel.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        binding.btnRecentPage.setOnClickListener {
            val intent = Intent(this, RecentPageActivity::class.java)
            startActivity(intent)
        }

        binding.btnFilter.setOnClickListener {
            showFilterDialog()
        }
        showLoading(false)
        fetchArticles()
        viewModel.getNewsSite()

        binding.searchbox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchArticles(query)
                    if (!hasSavedSearch) {
                        viewModel.saveRecentSearch(query)
                        hasSavedSearch = true
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Search", newText.orEmpty())
                return true
            }
        })
    }

    private fun showFilterDialog() {
        val categories = viewModel.newsSite.value?.data?.newsSites ?: emptyList()
        val categoryArray = categories.toTypedArray()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose a category")
            .setItems(categoryArray) { dialog, which ->
                selectedCategory = categoryArray[which]
                Log.d("SelectedCategory", selectedCategory.orEmpty())
                fetchArticles(selectedCategory)
            }

        builder.create().show()
    }

    private fun fetchArticles(query: String? = null) {
        lifecycleScope.launch {
            viewModel.getArticles(query, selectedCategory).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun searchArticles(query: String) {
        lifecycleScope.launch {
            viewModel.getArticles(query, selectedCategory).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.apply {
            visibility = if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.searchbox.clearFocus()
    }

    override fun onResume() {
        super.onResume()
        hasSavedSearch = false
    }

}
