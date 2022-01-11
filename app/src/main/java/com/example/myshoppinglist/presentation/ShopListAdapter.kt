package com.example.myshoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.myshoppinglist.R
import com.example.myshoppinglist.databinding.ItemShopDisabledBinding
import com.example.myshoppinglist.databinding.ItemShopEnabledBinding
import com.example.myshoppinglist.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val TAG = "ShopListAdapter"
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_POOL_SIZE = 20
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            SHOP_ITEM_ENABLED -> R.layout.item_shop_enabled
            SHOP_ITEM_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        with(binding.root) {
            setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
            setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
        }
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            SHOP_ITEM_ENABLED
        } else {
            SHOP_ITEM_DISABLED
        }
    }
}