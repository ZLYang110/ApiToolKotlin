package com.zlyandroid.apitoolkotlin.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

/**
 * @author zhangliyang
 * @date 2017/12/12
 * GitHub: https://github.com/ZLYang110
 * desc: 暗黑模式
 */
object NightModeUtils {
    fun isNightMode(config: Configuration): Boolean {
        val uiMode = config.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return uiMode == Configuration.UI_MODE_NIGHT_YES
    }

    fun isNightMode(context: Context): Boolean {
        return isNightMode(context.resources.configuration)
    }

    fun initNightMode() {
        if (SettingUtils.isSystemTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            if (SettingUtils.isDarkTheme()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}