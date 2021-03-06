package com.zlyandroid.module.weather.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.zlyandroid.basic.common.util.PreUtils
import com.zlyandroid.basic.common.util.log.L
import com.zlyandroid.module.weather.core.Data
import com.zlyandroid.module.weather.db.ApiDb

/**
 * author: zhangliyang
 * date: 2020/11/12
 */

object AppConfig {

    var sHandler: Handler = Handler()
    var debug = true
    private lateinit var application: Application

    fun getContext(): Context {
        if (application == null) {
            throw RuntimeException("mContext未在Application中初始化")
        }
        return application
    }


    fun init(mapplication: Application){
        application = mapplication

        //初始化缓存
        PreUtils.init(mapplication)

        //日志
        L.init("apiLog", debug)

        //数据库初始化
        ApiDb.init(mapplication)

       // Data.init()
    }


}