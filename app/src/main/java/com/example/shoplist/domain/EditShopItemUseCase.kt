package com.example.shoplist.domain

class EditShopItemUseCase(private val repository:Repository){
    suspend fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }
}