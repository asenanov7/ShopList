package com.example.shoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MainViewModel
    private lateinit var adapter:ShopItemAdapter
    private lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            adapter.listAdapter = it
        }

        setupClickAndSwipeListeners()
    }

    private fun setupRecycler(){
        recyclerView = findViewById(R.id.rvShopItems)
        adapter = ShopItemAdapter()
        recyclerView.adapter = adapter
        with(recyclerView) {
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_disabled, ShopItemAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(R.layout.shop_item_enabled, ShopItemAdapter.MAX_POOL_SIZE)
        }
    }

    private fun setupClickAndSwipeListeners(){
        adapter.onItemLongClickListener = {
            viewModel.editEnableState(it)
        }
        adapter.onItemClickListener = {
            viewModel.editShopItem(it)
        }

        val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = viewModel.shopList.value?.get(viewHolder.adapterPosition)
                item?.let { viewModel.removeItem(it) }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}