package com.example.shoplist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase: GetShopItemUseCase,
    private val addShopItemUseCase: AddShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
) : ViewModel() {

    private val _errorInputNameLD = MutableLiveData<String>()
    val errorInputNameLD: LiveData<String>
        get() = _errorInputNameLD

    private val _errorInputCountLD = MutableLiveData<String>()
    val errorInputCountLD: LiveData<String>
        get() = _errorInputCountLD

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    private val _screenShouldBeFinishedLD = MutableLiveData<Unit>()
    val screenShouldBeFinishedLD: LiveData<Unit>
        get() = _screenShouldBeFinishedLD


    fun getShopItem(shopItemID: Int) {
        viewModelScope.launch {
            _shopItemLD.value = getShopItemUseCase.getShopItem(shopItemID)
        }
    }

    fun addShopItem(name: String?, count: String?) {

        val nameChecked = name?.trim() ?: ""
        val countChecked = count?.trim()?.toIntOrNull() ?: 0

        if (validateInput(nameChecked, countChecked)) {
            viewModelScope.launch {
                val shopItem = ShopItem(name = nameChecked, count = countChecked, enabled = true)
                addShopItemUseCase.addShopItem(shopItem)
                _screenShouldBeFinishedLD.value = Unit
            }
        }
    }

    fun editShopItem(name: String?, count: String?) {
        val nameChecked = name?.trim() ?: ""
        val countChecked = count?.trim()?.toIntOrNull() ?: 0

        if (validateInput(nameChecked, countChecked)) {
            viewModelScope.launch {
                _shopItemLD.value?.let {
                    val newShopItem = it.copy(name = nameChecked, count = countChecked)
                    editShopItemUseCase.editShopItem(newShopItem)
                    _screenShouldBeFinishedLD.value = Unit
                }
            }
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isEmpty()) {
            result = false
            _errorInputNameLD.value = "Name field must not be empty"
        }
        if (count <= 0) {
            result = false
            _errorInputCountLD.value = "Invalid input format"
        }
        return result

    }


}