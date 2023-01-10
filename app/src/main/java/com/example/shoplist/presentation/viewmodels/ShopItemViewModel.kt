package com.example.shoplist.presentation.viewmodels

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)


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
        _shopItemLD.value = getShopItemUseCase.getShopItem(shopItemID)
    }

    fun addShopItem(name: String?, count: String?) {
        val nameChecked = name?.trim() ?: ""
        val countChecked = count?.trim()?.toIntOrNull() ?: 0

        if (validateInput(nameChecked, countChecked)) {
            val shopItem = ShopItem(nameChecked, countChecked, true)
            addShopItemUseCase.addShopItem(shopItem)
            _screenShouldBeFinishedLD.value = Unit
        }
    }

    fun editShopItem(name: String?, count: String?) {
        val nameChecked = name?.trim() ?: ""
        val countChecked = count?.trim()?.toIntOrNull() ?: 0

        if (validateInput(nameChecked, countChecked)) {
            _shopItemLD.value?.let {
                val newShopItem = it.copy(name = nameChecked, count = countChecked)
                editShopItemUseCase.editShopItem(newShopItem)
                _screenShouldBeFinishedLD.value = Unit
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