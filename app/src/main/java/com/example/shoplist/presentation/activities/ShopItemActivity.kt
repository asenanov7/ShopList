package com.example.shoplist.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem.Companion.UNDEFINED_ID
import com.example.shoplist.presentation.fragments.ShopItemFragment

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.ShouldCloseFragmentListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item_actvity)

        if (savedInstanceState == null) {
            launchFragment()
        }
    }

    private fun launchFragment(){
        val fragment = when(intent.getStringExtra(KEY_MODE)){
            ADDING_MODE -> ShopItemFragment.newInstanceAdd()
            EDIT_MODE -> ShopItemFragment.newInstanceEdit(intent.getIntExtra(KEY_ID, UNDEFINED_ID))
            else -> throw Exception("Unknown mode")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewShopItemActivity, fragment)
            .commit()
    }

    override fun shouldCloseFragment() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object {
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
