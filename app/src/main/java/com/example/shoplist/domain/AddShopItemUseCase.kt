package com.example.shoplist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor (private val repository:Repository) {
    suspend fun addShopItem(shopItem: ShopItem){
        repository.addShopItem(shopItem)
    }
}