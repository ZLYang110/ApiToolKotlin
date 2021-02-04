package com.zlyandroid.module.weather.ui.presenter

import com.zlyandroid.basic.common.mvp.BasePresenter
import com.zlyandroid.basic.common.util.log.L
import com.zlyandroid.module.weather.core.Constants
import com.zlyandroid.module.weather.http.response.go
import com.zlyandroid.module.weather.ui.model.WeatherModel
import com.zlyandroid.module.weather.ui.view.WeatherView

/**
 * @author zhangliyang
 * @date 2021/1/14
 * GitHub: https://github.com/ZLYang110
 */
class WeatherPresenter : BasePresenter<WeatherView>() {
     var mModel = WeatherModel()

    fun now(location: String) {
        mModel.nowWeather(location, Constants.weatherKey).go(mModel, mView,isShowLoading = false, onSuccess = {
            L.i("OkHttp","回来了")
            L.i("OkHttp","回来了==="+it.now)
            mView?.nowSuccess(it.now,it.updateTime)
        },onError = {
            mView?.nowFailed()
        })
    }
    fun query24h(location: String) {
        mModel.query24h(location, Constants.weatherKey).go(mModel, mView,isShowLoading = false, onSuccess = {
            L.i("OkHttp","回来了")
            mView?.query24hSuccess(it.hourly)
        },onError = {
            mView?.query24hFailed()
        })
    }
    fun query7d(location: String) {
        mModel.query7d(location, Constants.weatherKey).go(mModel, mView,isShowLoading = false, onSuccess = {
            L.i("OkHttp","回来了")
            mView?.query7dSuccess(it.daily)
        },onError = {
            mView?.query7dFailed()
        })
    }
}