package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myshoppinglist.R
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        const val TAG = "ShopListAdapter"
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_POOL_SIZE = 20
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_ENABLED -> R.layout.item_shop_enabled
            SHOP_ITEM_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        with(holder) {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            view.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
            view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }

            if (shopItem.enabled) {
                tvName.setTextColor(
                    ContextCompat.getColor(holder.view.context, android.R.color.holo_red_light)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) {
            SHOP_ITEM_ENABLED
        } else {
            SHOP_ITEM_DISABLED
        }
    }


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}