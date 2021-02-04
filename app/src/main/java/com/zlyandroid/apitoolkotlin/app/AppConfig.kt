package com.zlyandroid.apitoolkotlin.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.zlyandroid.apitoolkotlin.core.Data
import com.zlyandroid.apitoolkotlin.db.ApiDb
import com.zlyandroid.apitoolkotlin.util.NightModeUtils
import com.zlyandroid.basic.common.util.PreUtils
import com.zlyandroid.basic.common.util.log.L

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

        //初始化数据
        Data.init()

        //路由初始化
        initARouter(mapplication)

        //暗黑模式
        initDarkMode()

        //数据库初始化
        ApiDb.init(mapplication)

    }

  private fun initARouter(mapplication: Application){
       if(debug){
           ARouter.openLog() // 打印日志
           ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
       }
        ARouter.init(mapplication) // 尽可能早，推荐在Application中初始化
    }

    fun initDarkMode() {
        NightModeUtils.initNightMode()
    }


}