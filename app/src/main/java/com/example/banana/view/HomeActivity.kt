package com.example.banana.view

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.banana.data.NewsListItem
import com.example.banana.adapter.BannerConcatAdapter
import com.example.banana.adapter.NewsAdapter
import com.example.banana.databinding.ActivityHomeBinding
import com.example.banana.data.HomeViewModel
import kotlinx.coroutines.Runnable
import java.time.LocalDateTime

class HomeActivity : AppCompatActivity() {
    private val bannerConcatAdapter = BannerConcatAdapter(this)
    private val newsAdapter = NewsAdapter()
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private val scrollRunnable = object : Runnable {
        override fun run() {
            val holder = binding.rvHome.findViewHolderForAdapterPosition(0)
            if (holder is BannerConcatAdapter.ViewHolder) {
                val vp = holder.binding.vp2Inner
                vp.currentItem = vp.currentItem + 1
            }
            autoScrollHandler.postDelayed(this, 3000)
        }
    }
    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initViewModel()
        initGetLatestNews()
        initGetAllNews()
        initEvent()
        updateDateAndGreeting()
    }


    private fun initView() {

    }

    private fun initEvent() {
        bannerConcatAdapter.onItemClick={topStory->
            val intent = Intent(this, TopDetailActivity::class.java)
            intent.putExtra("id",topStory.id)
            intent.putExtra("url",topStory.url)
            startActivity(intent)
        }
        newsAdapter.onItemClick= { story ->
            val pureStoryList=viewModel.allNews.value?.filterIsInstance<NewsListItem.StoryItem>()?:emptyList()
            val idList= ArrayList(pureStoryList.map { it.id.toInt() })
            val position = pureStoryList.indexOfFirst { it.id == story.id }
            val intent = Intent(this, DetailActivity::class.java).apply {
                putIntegerArrayListExtra("idList",idList)
                putExtra("position",position)
                putExtra("url",story.url)
            }
            startActivity(intent)

        }
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                Log.d("点击测试", "点击到了新闻")
                if (!viewModel.isLoading.value!! && lastVisible >= totalItemCount - 2) {
                    viewModel.loadBeforeNews()
                }
            }
        })
    }

    private fun initAdapter(){
        binding.rvHome.layoutManager = LinearLayoutManager(this)
        val concatAdapter = ConcatAdapter(
            bannerConcatAdapter,
            newsAdapter
        )
        binding.rvHome.adapter = concatAdapter

    }
    private fun initGetLatestNews() {
        viewModel.loadLatestNews()
    }

    private fun initGetAllNews() {
        viewModel.loadBeforeNews()
    }

    private fun initViewModel() {
        viewModel.latestNews.observe(this) { data ->
            bannerConcatAdapter.setData(data.top_stories)
            bannerConcatAdapter.onUserDraggingListener = { isDragging ->
                if (isDragging)
                    stopAutoScroll()
                else
                    startAutoScroll()
            }
            startAutoScroll()
        }
        viewModel.allNews.observe(this) { list->
            newsAdapter.setData(list)
            Log.d("数据", "新闻数量 = ${list.size}")
        }

    }

    private fun startAutoScroll() {
        autoScrollHandler.removeCallbacks(scrollRunnable)
        autoScrollHandler.postDelayed(scrollRunnable, 3000)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(scrollRunnable)
    }
    private fun updateDateAndGreeting() {
        val now = LocalDateTime.now()
        val day = now.dayOfMonth // 日（数字）
        val month = now.month // 月份（英文枚举）
        val monthCn = when(month) {
            java.time.Month.JANUARY -> "一月"
            java.time.Month.FEBRUARY -> "二月"
            java.time.Month.MARCH -> "三月"
            java.time.Month.APRIL -> "四月"
            java.time.Month.MAY -> "五月"
            java.time.Month.JUNE -> "六月"
            java.time.Month.JULY -> "七月"
            java.time.Month.AUGUST -> "八月"
            java.time.Month.SEPTEMBER -> "九月"
            java.time.Month.OCTOBER -> "十月"
            java.time.Month.NOVEMBER -> "十一月"
            java.time.Month.DECEMBER -> "十二月"
        }

        // 3. 更新问候语
        val hour = now.hour
        val greeting = when (hour) {
            in 0..5 -> "凌晨好！"
            in 6..11 -> "早上好！"
            in 12..17 -> "下午好！"
            else -> "晚上好！"
        }
        binding.tvDay.text = day.toString()
        binding.tvMonth.text = monthCn
        binding.tvGreeting.text = greeting
    }

}