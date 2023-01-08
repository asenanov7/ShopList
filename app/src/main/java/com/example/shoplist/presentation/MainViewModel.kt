package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemListUseCase
import com.example.shoplist.domain.RemoveShopItemUseCase
import com.example.shoplist.domain.ShopItem

class MainViewModel:ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopItemListUseCase.getShopItemList()

    fun removeItem(shopItem: ShopItem){
        removeShopItemUseCase.removeShopItem(shopItem)
    }

    fun editStateItem(shopItem: ShopItem) {
        val shopItemEnableChange = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopItemEnableChange)
    }

}