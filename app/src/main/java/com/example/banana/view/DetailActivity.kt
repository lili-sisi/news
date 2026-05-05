package com.example.banana.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.banana.data.NewsListItem
import com.example.banana.R
import com.example.banana.adapter.DetailAdapter
import com.example.banana.adapter.TopDetailAdapter
import com.example.banana.databinding.ActivityNewsdetailBinding
import com.example.banana.data.HomeViewModel

class DetailActivity : AppCompatActivity() {
    private  var adapter: DetailAdapter?=null
    private  var storyId: List<Int>?=null
    private var currentPosition =0
    private  var newsUrl: String?=null
    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private lateinit var binding: ActivityNewsdetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPosition=intent.getIntExtra("position",0)
        newsUrl = intent.getStringExtra("url")
        storyId = intent.getIntegerArrayListExtra("idList")
        initViewModel()
        initEvent()
    }

    private fun initEvent() {
        binding.ivComment.setOnClickListener {
            val currentNewsId = storyId?.getOrNull(currentPosition) ?: 0
            if (currentNewsId != 0) {
                CommentActivity.startActivity(this, currentNewsId.toLong())
            }
        }
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
                val currentNews = viewModel.allNews.value
                    ?.filterIsInstance<NewsListItem.StoryItem>()
                    ?.getOrNull(position)

                newsUrl = currentNews?.url ?: ""

            }
        })
    }

    private fun initViewModel() {
        viewModel.loadLatestNews()
        viewModel.loadBeforeNews()
        viewModel.allNews.observe(this) { allNew ->

            Log.d("shu", "数据返回条数：${allNew.size}")
            val newsList = allNew.filterIsInstance<NewsListItem.StoryItem>()
            if (newsList.isEmpty()) return@observe
            if (adapter==null){
                adapter = DetailAdapter(newsList)
                binding.vp2.adapter = adapter
                binding.vp2.setCurrentItem(currentPosition, false)
            }
            else {

                adapter!!.updateData(newsList)
            }

        }


    }
}