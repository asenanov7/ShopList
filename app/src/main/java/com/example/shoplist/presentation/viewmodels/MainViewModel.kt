package com.example.shoplist.presentation.viewmodels

import android.app.Application
import androidx.core.graphics.scaleMatrix
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemListUseCase
import com.example.shoplist.domain.RemoveShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(application:Application): AndroidViewModel(application) {
    //Правильнее через даггер, это не клин. Потому что presentation слой не должен знать о data
    private val repository = ShopListRepositoryImpl(application)

    private val scope = CoroutineScope(Dispatchers.Main)

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


    val shopList = getShopItemListUseCase.getShopItemList()

    fun removeItem(shopItem: ShopItem){
        scope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun editEnableState(shopItem: ShopItem) {
        scope.launch {
            val shopItemEnableChange = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopItemEnableChange)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}