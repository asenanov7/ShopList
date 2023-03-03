package com.example.shoplist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor (private val repository:Repository){
    suspend fun editShopItem(shopItem: ShopItem){
        repository.editShopItem(shopItem)
    }
}