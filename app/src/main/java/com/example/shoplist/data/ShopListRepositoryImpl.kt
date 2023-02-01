package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : Repository {

    private val dao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = Mapper()
    override fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun removeShopItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        val shopItemDbModel = dao.getShopItem(shopItemID)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

    override fun editShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        val shopItemDbModelList = dao.getShopList()
        val mediator = MediatorLiveData<List<ShopItem>>()
        mediator.addSource(shopItemDbModelList) {
            mediator.value = mapper.mapListDbModelToEntity(it)
        }
        return mediator
    }
}
