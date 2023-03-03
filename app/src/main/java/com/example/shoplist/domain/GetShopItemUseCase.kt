package com.example.shoplist.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val repository:Repository) {
    suspend fun getShopItem(shopItemID: Int): ShopItem{
       return repository.getShopItem(shopItemID)
    }
}