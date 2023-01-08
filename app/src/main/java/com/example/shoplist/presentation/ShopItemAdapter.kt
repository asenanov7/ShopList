package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopItemAdapter:Adapter<ShopItemAdapter.ShopItemViewHolder>() {
    var listAdapter:List<ShopItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ShopItemViewHolder(itemView:View):ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.nameShopItem)
        val count:TextView = itemView.findViewById(R.id.сountShopItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(viewType,parent, false)
        return ShopItemViewHolder(itemView)
      }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = listAdapter[position]
        holder.name.text = shopItem.name
        holder.count.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            TODO()
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = listAdapter[position]
        return if (item.enabled) R.layout.shop_item_enabled else R.layout.shop_item_disabled
    }

    override fun getItemCount(): Int {
        return listAdapter.size
    }

    companion object{
        const val MAX_POOL_SIZE = 20
    }
}