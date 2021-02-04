package com.zlyandroid.apitoolkotlin.ui.mine.view

import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.basic.common.mvp.BaseView


/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
interface UserInfoView : BaseView {

    fun skillSuccess(data: MutableList<String>)
    fun skillFailed()

    fun hobbySuccess(data: MutableList<String>)
    fun hobbyFailed()
}