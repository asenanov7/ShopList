package com.example.shoplist.domain

class AddShopItemUseCase(private val repository:Repository) {
    suspend fun addShopItem(shopItem: ShopItem){
        repository.addShopItem(shopItem)
    }
}