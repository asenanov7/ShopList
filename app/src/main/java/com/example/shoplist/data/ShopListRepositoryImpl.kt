package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem

object ShopListRepositoryImpl : Repository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementID = 0
    private val shopListLD = MutableLiveData<List<ShopItem>>()

    override fun getShopItemList(): LiveData<List<ShopItem>>{
        return shopListLD
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncrementID++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun removeShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        return shopList.find { it.id == shopItemID }
            ?:throw java.lang.RuntimeException("shopItem with id:$shopItemID, not found")
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
        updateList()  //Обновление уже делатся в методе добавления, можно не париться
    }

    private fun updateList(){
        shopListLD.value = shopList.toList()
    }
}