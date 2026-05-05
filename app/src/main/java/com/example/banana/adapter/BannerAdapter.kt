package com.example.banana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banana.data.TopStory
import com.example.banana.databinding.ItemBannerBinding
import kotlin.Int.Companion.MAX_VALUE

 class BannerAdapter (var list : List<TopStory>,val mContext: Context): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
     fun updateData(newList: List<TopStory>) {
         this.list = newList
         notifyDataSetChanged()
     }

     var onItemClick:((TopStory)-> Unit)?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerAdapter.ViewHolder {
        val binding= ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
        val item=list[position%list.size]
        holder.mTitle.text=item.title
        holder.mAuthor.text=item.hint
        Glide.with(mContext)
            .load(item.image)
            .into(holder.mImage)

    }

    override fun getItemCount(): Int {
        return MAX_VALUE
    }
    inner class ViewHolder(binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root){
       val mTitle=binding.tvTitle
        val  mAuthor=binding.tvAuthor
        val  mImage=binding.ivBanner
        init {
            mImage.setOnClickListener {
                val position=bindingAdapterPosition
                val realItem=list[position%list.size]
                onItemClick?.invoke(realItem)
            }
        }

    }

}