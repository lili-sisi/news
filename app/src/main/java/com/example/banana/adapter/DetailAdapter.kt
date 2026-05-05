package com.example.banana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.banana.data.NewsListItem
import com.example.banana.databinding.ItemWebviewBinding

class DetailAdapter(private var newsList: List<NewsListItem.StoryItem>): RecyclerView.Adapter<DetailAdapter.ViewHoler>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailAdapter.ViewHoler {
        return ViewHoler(
            ItemWebviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
    fun updateData(newList: List<NewsListItem.StoryItem>) {
        this.newsList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DetailAdapter.ViewHoler, position: Int) {
        holder.bind(newsList[position].url)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHoler(private val binding: ItemWebviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
      fun bind(url: String) {
            with(binding.webview) {
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        }



    }
}