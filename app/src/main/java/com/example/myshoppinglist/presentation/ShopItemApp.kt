package com.example.myshoppinglist.presentation

import android.app.Application
import com.example.myshoppinglist.di.DaggerApplicationComponent

class ShopItemApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}