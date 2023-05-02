package com.example.phntrangtrongrcy.presenter

import android.content.Context
import android.util.Log
import com.example.phntrangtrongrcy.contract.MainActivityContract
import com.example.phntrangtrongrcy.model.api.ApiInterface
import com.example.phntrangtrongrcy.model.entity.Result
 import kotlinx.coroutines.*

class MainActivityPresenter  : MainActivityContract.Presenter {
     @OptIn(DelicateCoroutinesApi::class)
    override fun getData(page: Int, callback: (List<Result>) -> Unit) {
         GlobalScope.launch(Dispatchers.IO) {
            val apiService = ApiInterface.apiInterface
            try {
                val users = apiService.getData(page)
                 callback(users.results)
            } catch (e: Exception) {
                callback(emptyList())
                Log.d("resulet",e.toString())
            }
        }.start()
    }
}