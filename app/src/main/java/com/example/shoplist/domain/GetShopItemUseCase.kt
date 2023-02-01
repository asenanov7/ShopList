package com.example.shoplist.domain

class GetShopItemUseCase(private val repository:Repository) {
    suspend fun getShopItem(shopItemID: Int): ShopItem{
       return repository.getShopItem(shopItemID)
    }
}