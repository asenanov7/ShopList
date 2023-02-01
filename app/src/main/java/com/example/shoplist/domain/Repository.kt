package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface Repository {
    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    fun getShopItemList(): LiveData<List<ShopItem>>

    suspend fun getShopItem(shopItemID: Int): ShopItem

    suspend fun removeShopItem(shopItem: ShopItem)
}