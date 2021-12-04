package com.example.myshoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
    private var shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ShopItem("Name $i", i, true)
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = shopList.get(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(id: Int): ShopItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Element $id not found")

    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }
}