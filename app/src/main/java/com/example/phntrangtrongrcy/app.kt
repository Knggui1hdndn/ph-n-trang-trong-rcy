package com.example.phntrangtrongrcy

import android.app.Application
import android.content.Context

class app : Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context= this.applicationContext
    }
}