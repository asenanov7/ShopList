package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import javax.inject.Inject

class Mapper @Inject constructor() {

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