package com.example.shoplist.domain

class GetShopItemListUseCase(private val repository: Repository){
    fun getShopItemList():List<ShopItem>{
        return repository.getShopItemList()
    }
}
