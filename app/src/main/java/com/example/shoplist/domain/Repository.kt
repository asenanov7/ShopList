package com.example.shoplist.domain

import androidx.lifecycle.LiveData

interface Repository {
    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemList(): LiveData<List<ShopItem>>

    fun getShopItem(shopItemID: Int): ShopItem

    fun removeShopItem(shopItem: ShopItem)
}