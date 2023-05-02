package com.example.phntrangtrongrcy.view

import android.annotation.SuppressLint
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phntrangtrongrcy.R
import com.example.phntrangtrongrcy.WifiStateListener
import com.example.phntrangtrongrcy.model.api.ApiInterface
import com.example.phntrangtrongrcy.model.entity.QuoteList
import com.example.phntrangtrongrcy.presenter.MainActivityPresenter
import com.example.phntrangtrongrcy.view.adapter.MainAdapter
import com.example.phntrangtrongrcy.view.adapter.MyLoadStateAdapter
import com.example.phntrangtrongrcy.view.adapter.QuoteRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

class MainActivity : AppCompatActivity() {
    private lateinit var rcy: RecyclerView

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcy = findViewById(R.id.rcy)
        val adapter = MainAdapter()
        rcy.layoutManager = LinearLayoutManager(this)
        rcy.setHasFixedSize(true)
        rcy.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = MyLoadStateAdapter { adapter.retry() },
            header = MyLoadStateAdapter { adapter.retry() }
        )

        // để lắng nghe sự thay đổi của PagingData được phát ra bởi MyPagingSource.
//         sử dụng lifecycleScope giúp đảm bảo rằng coroutine được thực thi trên vòng
//         đời của thành phần Android và tự động hủy bỏ khi thành phần bị hủy bỏ.
        adapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.Loading -> {
                    // Hiển thị progress bar hoặc animation
                    Log.d("sasss", "loadState" + "Loading")
                    findViewById<Button>(R.id.a).visibility = View.GONE

                }
                is LoadState.Error -> {
                    // Hiển thị thông báo lỗi
                    Log.d("sasss", "loadState" + "Error")
                    findViewById<Button>(R.id.a).visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    // Ẩn progress bar hoặc animation
                    Log.d("sasss", "loadState" + "NotLoading")
                    findViewById<Button>(R.id.a).visibility = View.GONE

                }
            }
        }
        registerReceiver(WifiStateListener(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))

        findViewById<Button>(R.id.a).setOnClickListener {
            adapter.retry()
        }
        MainActivityPresenter().getData(1){
            Log.d("dđffff",it.toString())

        }

        ViewModelProvider(this@MainActivity).get(QuoteRepository::class.java).pagingData.observe(this@MainActivity) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }


}