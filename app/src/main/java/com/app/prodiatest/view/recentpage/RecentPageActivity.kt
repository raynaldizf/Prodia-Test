package com.app.prodiatest.view.recentpage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.prodiatest.databinding.ActivityRecentPageBinding
import com.app.prodiatest.view.recentpage.adapter.RecentAdapter
import com.app.prodiatest.viewmodel.ArticleViewModel

class RecentPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecentPageBinding
    private val viewModel: ArticleViewModel by viewModels()
    private val adapter = RecentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        fetchData()
    }

    fun fetchData(){
        viewModel.getAllRecentSearches().observe(this) {
            adapter.submitList(it)
        }
    }
}
