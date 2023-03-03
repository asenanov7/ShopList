package com.example.shoplist

import android.app.Application
import android.content.Context
import com.example.shoplist.di.AppComponent
import com.example.shoplist.di.DaggerAppComponent

class AppClass:Application() {

    lateinit var component:AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }

}
val Context.component: AppComponent
    get() = when (this) {
        is AppClass -> component
        else -> this.applicationContext.component
    }