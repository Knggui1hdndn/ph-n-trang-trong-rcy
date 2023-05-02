package com.example.phntrangtrongrcy.view.adapter

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.registerReceiver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.paging.*
import com.example.phntrangtrongrcy.app
import com.example.phntrangtrongrcy.model.api.ApiInterface
import kotlinx.coroutines.currentCoroutineContext
import pagingSource

class QuoteRepository(private val postApi: ApiInterface) : ViewModel() {
    constructor() : this(ApiInterface.apiInterface)

    private fun getPagingSource() = pagingSource(postApi)


    init {
        var i = 0;
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1 != null) {

                    if (p1.getBooleanExtra("state", false) == true) {
                        Log.d("fffff", "ok")
                             onNetworkAvailable()

                        i++
                    }
                }
            }
        }
        app.context.registerReceiver(broadcastReceiver, IntentFilter("aaa"))
    }


    val pager = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { getPagingSource() }
    )

    val pagingData = pager.liveData.cachedIn(viewModelScope)

    fun onNetworkAvailable() {
         getPagingSource().aoj()
    }
}
