package com.app.prodiatest.view.homepage.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.prodiatest.data.model.response.ResultGetArticle
import com.app.prodiatest.databinding.CustomArtikelBinding
import com.app.prodiatest.view.detailarticle.DetailArticleActivity
import com.bumptech.glide.Glide

class HomeAdapter : PagingDataAdapter<ResultGetArticle, HomeAdapter.ViewHolder>(ARTICLE_COMPARATOR) {
    class ViewHolder(val binding: CustomArtikelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CustomArtikelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.binding.tvSummary.text = data.summary.substringBefore(".") + "."
            holder.binding.tvTitle.text = data.title
            Glide.with(holder.itemView.context)
                .load(data.imageUrl)
                .into(holder.binding.ivImage)

            holder.binding.cardView.setOnClickListener{
                val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
                intent.putExtra("id", data.id)
                holder.itemView.context.startActivity(intent)

            }
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<ResultGetArticle>() {
            override fun areItemsTheSame(oldItem: ResultGetArticle, newItem: ResultGetArticle): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ResultGetArticle, newItem: ResultGetArticle): Boolean =
                oldItem == newItem
        }
    }
}
