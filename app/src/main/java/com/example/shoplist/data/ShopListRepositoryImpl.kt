package com.example.shoplist.data

import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

object ShopListRepositoryImpl : Repository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementID = 0

    override fun getShopItemList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementID++
        }
        shopList.add(shopItem)
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        return shopList.find { it.id == shopItemID }
            ?:throw java.lang.RuntimeException("shopItem with id:$shopItemID, not found")
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }
}