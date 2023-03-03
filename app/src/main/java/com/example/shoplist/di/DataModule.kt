package com.example.shoplist.di

import android.app.Application
import com.example.shoplist.data.AppDatabase
import com.example.shoplist.data.ShopListDao
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl:ShopListRepositoryImpl) : Repository

    companion object {
        @ApplicationScope
        @Provides
        fun provideDatabaseDao(application: Application): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}