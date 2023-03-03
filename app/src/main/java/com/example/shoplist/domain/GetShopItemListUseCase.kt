package com.example.shoplist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopItemListUseCase @Inject constructor (private val repository: Repository){
    fun getShopItemList(): LiveData<List<ShopItem>> {
        return repository.getShopItemList()
    }
}
