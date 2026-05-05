package com.example.banana.adapter

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.example.banana.databinding.ItemWebviewBinding
import com.example.banana.viewmodel.TopDetailViewModel

class TopDetailAdapter (val viewModel: TopDetailViewModel): RecyclerView.Adapter<TopDetailAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ItemWebviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TopDetailAdapter.ViewHolder, position: Int) {
        holder.bind(viewModel.topList.value!![position].url)
    }

    override fun getItemCount(): Int {
        return viewModel.topList.value!!.size
    }
    inner class ViewHolder(val binding: ItemWebviewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(mUrl: String){
            binding.tvTest.text = "当前 URL：$mUrl"
            with(binding.webview){
                settings.cacheMode= WebSettings.LOAD_NO_CACHE
                settings.javaScriptEnabled=true
                webViewClient= WebViewClient()
                loadUrl(mUrl)
            }

        }


    }
}