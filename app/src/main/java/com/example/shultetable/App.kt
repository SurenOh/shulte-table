package com.example.shultetable

import android.app.Application
import com.example.shultetable.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(applicationModule))
        }
    }
}