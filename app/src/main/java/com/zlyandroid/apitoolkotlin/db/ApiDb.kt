package com.zlyandroid.apitoolkotlin.db

import android.content.Context
import androidx.room.Room


/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:DB
 */

object ApiDb {
    private var context: Context? = null
    private var database: ApiRoom? = null

    @JvmStatic
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    @JvmStatic
    private fun db(dbName: String): ApiRoom {
        database?.run {
            if (isOpen) {
                if (openHelper.databaseName == dbName) {
                    return this
                } else {
                    close()
                }
            }
        }
        database = Room.databaseBuilder(context!!, ApiRoom::class.java, dbName)
            .build()
        return database!!
    }

    @JvmStatic
    fun db(): ApiRoom {
        return db("zly_db")
    }
}