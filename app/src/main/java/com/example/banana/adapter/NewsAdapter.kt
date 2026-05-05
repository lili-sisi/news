package com.example.banana.adapter

import android.media.RouteListingPreference
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banana.data.NewsListItem
import com.example.banana.databinding.ItemDataBinding
import com.example.banana.databinding.ItemNewsBinding

class NewsAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> () {
    private var items = mutableListOf<NewsListItem>()

    companion object {
        const val TYPE_DATA = 0
        const val TYPE_STORY = 1
    }

    var onItemClick: ((NewsListItem.StoryItem) -> Unit)? = null
    fun setData(newsList: List<NewsListItem>) {
        items.clear()
        items.addAll(newsList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        if (items[position] is NewsListItem.DataHeader) TYPE_DATA else TYPE_STORY

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_DATA) {
            DateViewHolder(ItemDataBinding.inflate(inflater, parent, false))
        } else {
            StoryViewHolder(ItemNewsBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val currentItem = items[position]
        if (holder is DateViewHolder && currentItem is NewsListItem.DataHeader) {
            holder.bind(currentItem)
        } else if (holder is StoryViewHolder && currentItem is NewsListItem.StoryItem) {
            holder.bind(currentItem)

        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class DateViewHolder(val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsListItem.DataHeader) {
            binding.tvDataHead.text = item.dateText
        }
    }

    // 新闻布局
    inner class StoryViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsListItem.StoryItem) {
            binding.apply {
                tvNewsHint.text = item.hint
                tvNewsTitle.text = item.title
                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(ivNewsImage)
            }

        }

        init {
            itemView.setOnClickListener {
                var position=bindingAdapterPosition
                var nowItem=items[position]
                onItemClick?.invoke(nowItem as NewsListItem.StoryItem)
            }
        }


    }
}