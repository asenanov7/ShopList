package com.example.shoplist.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ShopItemViewModel(application:Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val scope = CoroutineScope(Dispatchers.Main)

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
        scope.launch {
            _shopItemLD.value = getShopItemUseCase.getShopItem(shopItemID)
        }
    }

    fun addShopItem(name: String?, count: String?) {

            val nameChecked = name?.trim() ?: ""
            val countChecked = count?.trim()?.toIntOrNull() ?: 0

            if (validateInput(nameChecked, countChecked)) {
                scope.launch {
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
            scope.launch {
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