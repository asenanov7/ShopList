package com.example.shoplist.domain

import javax.inject.Inject

class RemoveShopItemUseCase @Inject constructor (private val repository:Repository) {
    suspend fun removeShopItem(shopItem: ShopItem){
        repository.removeShopItem(shopItem)
    }
}