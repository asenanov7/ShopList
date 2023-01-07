package com.example.shoplist.domain

class EditShopItemUseCase(private val repository:Repository){
    fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }
}