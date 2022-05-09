package com.example.myshoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.myshoppinglist.domain.ShopItem
import com.example.myshoppinglist.presentation.ShopItemApp
import javax.inject.Inject

class ShopListProvider : ContentProvider() {

    private val component by lazy {
        (context as ShopItemApp).component
    }

    @Inject
    lateinit var mapper: ShopListMapper

    @Inject
    lateinit var shopListDao: ShopListDao

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.myshoppinglist", "shop_items/", URI_QUERY)
        addURI("com.example.myshoppinglist", "shop_items/#", URI_QUERY1)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            URI_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else -> {
                null
            }
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            URI_QUERY -> {
                if (values == null) return null
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            URI_QUERY -> {
                val id = selectionArgs?.get(0)?.toInt() ?: -1
                return shopListDao.deleteShopItemSync(id)
            }
        }
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when (uriMatcher.match(uri)) {
            URI_QUERY -> {
                if (values == null) return -1
                val id = values.getAsInteger("id")
                val name = values.getAsString("name")
                val count = values.getAsInteger("count")
                val enabled = values.getAsBoolean("enabled")
                val shopItem = ShopItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                shopListDao.addShopItemSync(mapper.mapEntityToDbModel(shopItem))
            }
        }
        return -1
    }

    companion object {
        private const val URI_QUERY = 100
        private const val URI_QUERY1 = 101
    }
}