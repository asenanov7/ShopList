package com.example.shoplist.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoplist.presentation.viewmodels.ShopItemViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextCount: TextInputEditText
    private lateinit var buttonSave: Button
    private lateinit var shopItemViewModel: ShopItemViewModel

    private var screedMode:String? = UNDEFINED_SCREEN_MODE
    private var shopItemId = UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_actvity)
        initViews()
        shopItemViewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        screedMode = intent.getStringExtra(KEY_MODE)

        when(screedMode){
            ADDING_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }

        shopItemViewModel.errorInputNameLD.observe(this){textInputLayoutName.error = it}
        shopItemViewModel.errorInputCountLD.observe(this){textInputLayoutCount.error = it}
        textInputEditTextName.doBeforeTextChanged{text, start, count, after -> textInputLayoutName.error=null }
        textInputEditTextCount.doBeforeTextChanged{text, start, count, after -> textInputLayoutCount.error=null }

    }

    private fun launchAddMode(){
        buttonSave.setOnClickListener {
            shopItemViewModel.addShopItem(
                textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
        }

        shopItemViewModel.screenShouldBeFinishedLD.observe(this){
            finish()
        }
    }

    private fun launchEditMode(){
        shopItemId = intent.getIntExtra(KEY_ID, UNDEFINED_ID)

       shopItemViewModel.getShopItem(shopItemId)
        shopItemViewModel.shopItemLD.observe(this){
            textInputEditTextName.setText(it.name)
            textInputEditTextCount.setText(it.count.toString())
        }

        buttonSave.setOnClickListener {
            shopItemViewModel.editShopItem(
                textInputEditTextName.text.toString(), textInputEditTextCount.text.toString())
        }

        shopItemViewModel.screenShouldBeFinishedLD.observe(this){
            finish()
        }
    }

    private fun initViews() {
        textInputLayoutName = findViewById(R.id.textInputLayoutName)
        textInputLayoutCount =findViewById(R.id.textInputLayoutCount)
        textInputEditTextName = findViewById(R.id.textInputEditTextName)
        textInputEditTextCount = findViewById(R.id.textInputEditTextCount)
        buttonSave = findViewById(R.id.buttonSave)
    }

    companion object {
        private const val UNDEFINED_SCREEN_MODE = ""

        private const val KEY_MODE = "key_mode"
        private const val ADDING_MODE = "adding"
        private const val EDIT_MODE = "edit"
        private const val KEY_ID = "id_key"

        fun newIntentAddingMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(KEY_MODE, ADDING_MODE)
            return intent
        }

        fun newIntentEditMode(context: Context, ID: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(KEY_MODE, EDIT_MODE)
            intent.putExtra(KEY_ID, ID)
            return intent
        }
    }

}
