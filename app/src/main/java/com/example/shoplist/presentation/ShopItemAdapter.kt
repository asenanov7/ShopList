package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopItemAdapter() : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {
    companion object{
        const val MAX_POOL_SIZE = 20
    }

    lateinit var onItemLongClickListener:(ShopItem)->Unit
    lateinit var onItemClickListener:(ShopItem)->Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType,parent, false)
        return ShopItemViewHolder(itemView)
      }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.name.text = shopItem.name
        holder.count.text = shopItem.count.toString()

        holder.itemView.setOnLongClickListener {
            onItemLongClickListener(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            onItemClickListener(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) R.layout.shop_item_enabled else R.layout.shop_item_disabled
    }

}