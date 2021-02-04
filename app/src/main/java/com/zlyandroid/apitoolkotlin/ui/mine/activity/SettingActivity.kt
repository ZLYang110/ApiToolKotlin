package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CompoundButton
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.app.AppConfig
import com.zlyandroid.apitoolkotlin.util.SettingUtils
import com.zlyandroid.basic.common.app.App
import com.zlyandroid.basic.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * @author zhangliyang
 * @date 2017/12/12
 * GitHub: https://github.com/ZLYang110
 * desc: 设置
 */
class SettingActivity: BaseActivity() ,CompoundButton.OnCheckedChangeListener{

    private var mDarkTheme = false//暗黑模式
    private var mSystemTheme = false //跟随系统暗黑模式

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getLayoutID(): Int = R.layout.activity_setting

    override fun initView() {
        mDarkTheme = SettingUtils.isDarkTheme()
        sc_dark_theme.isChecked = mDarkTheme
        mSystemTheme = SettingUtils.isSystemTheme()
        changeEnable(!mSystemTheme, tv_dark_theme_title, sc_dark_theme)
        sc_system_theme.setChecked(mSystemTheme)
    }

    override fun initData() {
        sc_dark_theme.setOnCheckedChangeListener(this)
        sc_system_theme.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(v: CompoundButton, isChecked: Boolean) {
        when(v){
            sc_dark_theme -> {
                //暗黑模式
                SettingUtils.setDarkTheme(isChecked)
                AppConfig.initDarkMode()
                abc.foregroundLayer.visibility = View.VISIBLE
                v.postDelayed(Runnable {
                    abc.foregroundLayer.visibility =View.GONE
                    App.recreate()
                }, 2000)
            }
            sc_system_theme -> {
                //跟随系统暗黑模式
                changeEnable(!isChecked, tv_dark_theme_title, sc_dark_theme)
                SettingUtils.setSystemTheme(isChecked)
                AppConfig.initDarkMode()
                v.postDelayed(Runnable { App.restartApp() }, 300)
            }
        }
    }


    //设置某个view 不可用
    private fun changeEnable(enable: Boolean, vararg views: View) {
        for (view in views) {
            if (enable) {
                view.isEnabled = true
                view.alpha = 1f
            } else {
                view.isEnabled = false
                view.alpha = 0.5f
            }
        }
    }
}