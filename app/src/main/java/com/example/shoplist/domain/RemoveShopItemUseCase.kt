package com.example.shoplist.domain

class RemoveShopItemUseCase(private val repository:Repository) {
    fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }
}