package com.zlyandroid.apitoolkotlin.ui.home.presenter

import com.zlyandroid.apitoolkotlin.http.response.go
import com.zlyandroid.apitoolkotlin.ui.home.model.HomeModel
import com.zlyandroid.apitoolkotlin.ui.home.view.HomeView
import com.zlyandroid.basic.common.mvp.BasePresenter
import com.zlyandroid.basic.common.util.log.L

/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
class HomePresenter : BasePresenter<HomeView>() {
     var mModel = HomeModel()

    fun login(username: String, password: String) {
        mModel.login(username, password).go(mModel, mView,isShowLoading = true, onSuccess = {
            L.i(it.data)
            L.i(it.data.username)
            mView?.loginSuccess()
        })
    }

}