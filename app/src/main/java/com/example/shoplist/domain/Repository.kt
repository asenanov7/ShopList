package com.example.shoplist.domain

interface Repository {
    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItemList(): List<ShopItem>

    fun getShopItem(shopItemID: Int): ShopItem

    fun removeShopItem(shopItem: ShopItem)
}