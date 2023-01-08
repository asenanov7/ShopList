package com.example.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MainViewModel
    private lateinit var adapter:ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            adapter.listAdapter = it
        }

    }
    private fun setupRecycler(){
        val recyclerView:RecyclerView = findViewById(R.id.rvShopItems)
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
        with(recyclerView) {
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_disabled, ShopItemAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_enabled, ShopItemAdapter.MAX_POOL_SIZE)
        }
    }
}