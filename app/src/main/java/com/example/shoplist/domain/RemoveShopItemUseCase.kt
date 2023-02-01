package com.example.shoplist.domain

class RemoveShopItemUseCase(private val repository:Repository) {
    suspend fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }
}