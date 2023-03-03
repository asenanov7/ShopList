package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.Repository
import com.example.shoplist.domain.ShopItem
import javax.inject.Inject
import kotlin.random.Random

class ShopListRepositoryImpl @Inject constructor(
    private val dao: ShopListDao,
    private val mapper: Mapper,
) : Repository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun removeShopItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override suspend fun getShopItem(shopItemID: Int): ShopItem {
        val shopItemDbModel = dao.getShopItem(shopItemID)
        return mapper.mapDbModelToEntity(shopItemDbModel)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        val shopItemDbModelList = dao.getShopList()
        val mediator = MediatorLiveData<List<ShopItem>>()
        //Добавляем слушатель, какие данные будет слушать наш медиатор, и в лямбде, указываем что с этими данными делать если они изменились
        mediator.addSource(shopItemDbModelList) {
            mediator.value = mapper.mapListDbModelToEntity(it)
        }
        return mediator
    }
}
