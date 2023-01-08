package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import kotlin.random.Random

object ShopListRepositoryImpl : Repository {

    private val shopList = sortedSetOf<ShopItem>({o1, o2 -> o1.id.compareTo(o2.id)})
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementID = 0


    private fun updateList(){
        shopListLD.value = shopList.toList()
    }


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
        addShopItem(shopItem) //Обновление уже делается в методе удаления и добавления, можно не париться
    }


}