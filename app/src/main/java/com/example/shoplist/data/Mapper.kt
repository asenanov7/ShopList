package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class Mapper() {

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel):ShopItem {
       return ShopItem(
           shopItemDbModel.id,
           shopItemDbModel.name,
           shopItemDbModel.count,
           shopItemDbModel.enabled
       )
    }

    fun mapEntityToDbModel(shopItem:ShopItem):ShopItemDbModel {
        return ShopItemDbModel(
            shopItem.id,
            shopItem.name,
            shopItem.count,
            shopItem.enabled
        )
    }

    fun mapListDbModelToEntity(list: List<ShopItemDbModel>):List<ShopItem>{
        return list.map { mapDbModelToEntity(it) }
    }
}