package com.zlyandroid.apitoolkotlin.ui.home.view

import com.zlyandroid.basic.common.mvp.BaseView


/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
interface HomeView : BaseView {
    fun loginSuccess()
    fun loginFailed()
}