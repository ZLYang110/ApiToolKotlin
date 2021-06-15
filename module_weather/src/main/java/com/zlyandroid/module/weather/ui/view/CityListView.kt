package com.zlyandroid.module.weather.ui.view

import com.zlyandroid.basic.common.mvp.BaseView
import com.zlyandroid.module.weather.db.model.CityModel
import com.zlyandroid.module.weather.http.*


/**
 * @author zhangliyang
 * @date 2021/05/19
 * GitHub: https://github.com/ZLYang110
 */
interface CityListView : BaseView {
    fun getCityListSuccess(data: List<CityModel>)
    fun getCityListFailed()

    fun updataCitySuccess(data: CityModel)
    fun updataCityFailed()

    fun removeSuccess()
    fun removeFailed()
}