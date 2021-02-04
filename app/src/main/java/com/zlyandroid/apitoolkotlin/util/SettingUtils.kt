package com.zlyandroid.apitoolkotlin.util

import com.zlyandroid.basic.common.util.PreUtils

/**
 * @author zhangliyang
 * @date 2017/12/12
 * GitHub: https://github.com/ZLYang110
 * desc: 设置缓存
 */
object SettingUtils {
    private const val SP_NAME = "setting_key"
    private const val KEY_DARK_THEME = "KEY_DARK_THEME" // 暗黑模式
    private const val KEY_SYSTEM_THEME = "KEY_SYSTEM_THEME" // 跟随系统暗色模式

    private val mSPUtils: PreUtils = PreUtils.newInstance(SP_NAME)

    private var mDarkTheme = false //是否暗黑模式
    private var mSystemTheme = true //是否跟随系统


  /*  private object Holder {
        val INSTANCE: SettingUtils = SettingUtils
    }

    fun getInstance(): SettingUtils {
        return Holder.INSTANCE
    }*/

    init {
        mSystemTheme = mSPUtils[KEY_SYSTEM_THEME, mSystemTheme]
        mDarkTheme = mSPUtils[KEY_DARK_THEME, mDarkTheme]
    }


     fun setSystemTheme(systemTheme: Boolean) {
        mSystemTheme = systemTheme
        mSPUtils.save(KEY_SYSTEM_THEME, systemTheme)
    }

     fun isSystemTheme(): Boolean {
        return mSystemTheme
    }

      fun setDarkTheme(darkTheme: Boolean) {
        mDarkTheme = darkTheme
        mSPUtils.save(KEY_DARK_THEME, darkTheme)
    }

      fun isDarkTheme(): Boolean {
        return mDarkTheme
    }

}