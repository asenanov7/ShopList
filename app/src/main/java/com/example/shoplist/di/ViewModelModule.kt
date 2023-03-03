package com.example.shoplist.di

import androidx.lifecycle.ViewModel
import com.example.shoplist.presentation.viewmodels.MainViewModel
import com.example.shoplist.presentation.viewmodels.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindDetailViewModel(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    @Binds
    fun bindListOfCoinsViewModel(impl: ShopItemViewModel): ViewModel

}