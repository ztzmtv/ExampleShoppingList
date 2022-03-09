package com.example.myshoppinglist.di

import android.app.Application
import android.content.Context
import com.example.myshoppinglist.presentation.MainActivity
import com.example.myshoppinglist.presentation.ShopItemActivity
import com.example.myshoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: ShopItemActivity)

    fun inject(fragment: ShopItemFragment)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}