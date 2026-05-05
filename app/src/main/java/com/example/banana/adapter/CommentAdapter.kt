package com.example.banana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banana.data.Comment
import com.example.banana.databinding.ItemCommentBinding

class CommentAdapter( var list: MutableList<Comment> = mutableListOf()): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    fun updateData(newList: List<Comment>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged() // 关键：通知 RecyclerView 数据变了，重新绘制页面
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.ViewHolder {
        return ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.bind(list[position].content)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(binding: ItemCommentBinding):RecyclerView.ViewHolder(binding.root){
        val mContent = binding.tvComment
        fun bind(content: String) {
            mContent.text=content
        }
    }
}