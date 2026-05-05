package com.example.banana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.banana.data.TopStory
import com.example.banana.databinding.ItemRvBannerBinding

class BannerConcatAdapter ( val mContext: Context): RecyclerView.Adapter<BannerConcatAdapter.ViewHolder>() {
    var onUserDraggingListener: ((Boolean) -> Unit)? = null
    private var bannerData: List<TopStory> = emptyList()
    private var innerAdapter: BannerAdapter? = null
    fun setData(list: List<TopStory>) {
        this.bannerData = list
        innerAdapter?.updateData(list)
        notifyDataSetChanged()
    }
    var onItemClick:((TopStory)-> Unit)?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerConcatAdapter.ViewHolder {
        val binding = ItemRvBannerBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(bannerData, mContext)
    }

    override fun getItemCount() = if (bannerData.isEmpty()) 0 else 1
    inner class ViewHolder(val binding: ItemRvBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<TopStory>, context: Context) {
            if (binding.vp2Inner.adapter == null) {
                innerAdapter = BannerAdapter(data, context)
                binding.vp2Inner.adapter = innerAdapter
                innerAdapter?.onItemClick = { topStory ->
                    this@BannerConcatAdapter
                        .onItemClick?.invoke(topStory)}

                val mid = Int.MAX_VALUE / 2
                val startPos = mid - (mid % data.size)
                binding.vp2Inner.setCurrentItem(startPos, false)
                binding.vp2Inner.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback(){
                     override fun onPageScrollStateChanged(state: Int) {
                         when(state){
                             ViewPager2.SCROLL_STATE_DRAGGING->onUserDraggingListener?.invoke(true)
                             ViewPager2.SCROLL_STATE_IDLE->onUserDraggingListener?.invoke(false)
                         }
                     }
                    }

                )
                }

            }


        }
    }
