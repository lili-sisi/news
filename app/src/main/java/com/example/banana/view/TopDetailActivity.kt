package com.example.banana.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.banana.R
import com.example.banana.adapter.TopDetailAdapter
import com.example.banana.databinding.ActivityNewsdetailBinding
import com.example.banana.viewmodel.TopDetailViewModel

class TopDetailActivity : AppCompatActivity() {
    private  var adapter: TopDetailAdapter?=null
    private var newsUrl: String? = null
    private var newsId: Int?=null
    private lateinit var binding: ActivityNewsdetailBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[TopDetailViewModel::class.java]
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsId=intent.getIntExtra("id",-1)
        newsUrl = intent.getStringExtra("url")
        initViewModel()
        initEvent()
    }

    private fun initEvent() {
        binding.ivComment.setOnClickListener {
            if (newsId != null) {
                CommentActivity.startActivity(this, newsId!!.toLong())
            }
        }
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val currentItem = viewModel.topList.value?.getOrNull(position)
                newsUrl = viewModel.topList.value!![position].url
                newsId = currentItem?.id
            }
        })
    }

    private fun initViewModel() {
        viewModel.getTopMes()
        viewModel.topList.observe(this) { list ->
            Log.d("shujv_success", "数据返回条数：${list.size}")
                if (adapter==null){
                adapter = TopDetailAdapter(viewModel)
                binding.vp2.adapter = adapter}
                list.forEachIndexed { index, item ->
                    if (item.url == newsUrl) {
                        binding.vp2.setCurrentItem(index, false)
                    }
                }


        }
    }
}