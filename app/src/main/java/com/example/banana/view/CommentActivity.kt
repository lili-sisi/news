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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banana.R
import com.example.banana.adapter.CommentAdapter
import com.example.banana.databinding.ActivityCommentBinding
import com.example.banana.data.HomeViewModel

class CommentActivity : AppCompatActivity() {
    private val binding: ActivityCommentBinding by lazy {
        ActivityCommentBinding.inflate(layoutInflater)
    }
    private var adapter: CommentAdapter? = null
    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private var id: Long = -1
    private var count: Int = -1

    companion object {
        fun startActivity(context: Context, id: Long) {
            val intent = Intent(context, CommentActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initView()
        initViewModel()
        initEvent()
    }

    private fun initData() {
        id = intent.getLongExtra("id", -1)
        count = intent.getIntExtra("count", -1)
        Log.d("ld", "id=${id} , count=${count}");
    }
    fun initView(){
        binding.rvComment.layoutManager = LinearLayoutManager(this)
        adapter = CommentAdapter()
        binding.rvComment.adapter = adapter
    }

    fun initEvent() {}
    fun initViewModel() {
        viewModel.loadComment(id)
        viewModel.comment.observe(this) {list->
            if (list != null) {
                adapter?.updateData(list)
            }
        }
    }
}