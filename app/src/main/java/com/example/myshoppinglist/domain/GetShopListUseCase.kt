package com.example.myshoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val shopListRepository: ShopListRepository
) {

    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopList()
    }
}
