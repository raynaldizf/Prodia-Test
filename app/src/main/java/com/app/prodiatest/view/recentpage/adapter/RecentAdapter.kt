package com.app.prodiatest.view.recentpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.prodiatest.data.model.request.RecentSearchEntity
import com.app.prodiatest.databinding.RecentSearchListBinding

class RecentAdapter : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {
    private var search: List<RecentSearchEntity> = emptyList()

    class ViewHolder(val binding: RecentSearchListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecentSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = search[position]
        holder.binding.tvSearch.text = article.query
    }

    override fun getItemCount(): Int = search.size

    fun submitList(newSearch: List<RecentSearchEntity>) {
        search = newSearch
        notifyDataSetChanged()
    }
}
