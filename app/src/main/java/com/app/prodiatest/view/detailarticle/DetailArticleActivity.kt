package com.app.prodiatest.view.detailarticle

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.prodiatest.data.network.ApiResponse
import com.app.prodiatest.databinding.ActivityDetailArticleBinding
import com.app.prodiatest.viewmodel.ArticleViewModel
import com.bumptech.glide.Glide
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var viewModel: ArticleViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ArticleViewModel::class.java]

        val id = intent.getIntExtra("id", 0)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        detailArticle(id.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun detailArticle(id: String) {
        viewModel.getDetailArticle(id)
        viewModel.detailArticle.observe(this) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val data = response.data!!

                    binding.articleTitle.text = data.title
                    binding.articleSummary.text = data.summary.substringBefore(".") + "."
                    binding.articleSource.text = "Sumber : ${data.newsSite}"

                    val formattedDate = OffsetDateTime.parse(data.publishedAt)
                        .format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale.getDefault()))
                    binding.articlePublishedAt.text = formattedDate

                    Glide.with(this)
                        .load(data.imageUrl)
                        .into(binding.imageView)
                }

                is ApiResponse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ApiResponse.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
