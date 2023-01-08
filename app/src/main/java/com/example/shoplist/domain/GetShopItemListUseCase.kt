package com.example.shoplist.domain

import androidx.lifecycle.LiveData

class GetShopItemListUseCase(private val repository: Repository){
    fun getShopItemList(): LiveData<List<ShopItem>> {
        return repository.getShopItemList()
    }
}
