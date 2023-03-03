package com.example.shoplist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemListUseCase
import com.example.shoplist.domain.RemoveShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopItemListUseCase: GetShopItemListUseCase,
    private val removeShopItemUseCase: RemoveShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val shopList = getShopItemListUseCase.getShopItemList()

    fun removeItem(shopItem: ShopItem) {
        viewModelScope.launch {
            removeShopItemUseCase.removeShopItem(shopItem)
        }
    }

    fun editEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val shopItemEnableChange = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopItemEnableChange)
        }
    }

}