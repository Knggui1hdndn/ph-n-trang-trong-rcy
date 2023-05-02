package com.example.phntrangtrongrcy.model.api

import androidx.annotation.OptIn
import androidx.annotation.experimental.UseExperimental
import com.example.phntrangtrongrcy.model.entity.QuoteList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {

    companion object {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        val gson: Gson = GsonBuilder().setDateFormat("yyyyy-MM-dd").create()

        var apiInterface: ApiInterface =
            Retrofit.Builder()
                .baseUrl("https://quotable.io/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
                .create(ApiInterface::class.java)
    }


    @GET("quotes")
    suspend fun getDataAsync(@Query("page") page: Int): QuoteList

    @OptIn
    @GET("quotes")
    suspend fun getData(@Query("page") page: Int):  QuoteList
}