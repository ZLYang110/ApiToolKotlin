package com.zlyandroid.module.weather.ui.presenter


import android.app.Activity
import android.text.TextUtils
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.mvp.BasePresenter
import com.zlyandroid.basic.common.util.PreUtils
import com.zlyandroid.basic.common.util.log.L
import com.zlyandroid.module.weather.R
import com.zlyandroid.module.weather.core.AppCon
import com.zlyandroid.module.weather.core.Constants
import com.zlyandroid.module.weather.core.Data
import com.zlyandroid.module.weather.db.executor.CityListExecutor
import com.zlyandroid.module.weather.db.model.CityModel
import com.zlyandroid.module.weather.http.response.go
import com.zlyandroid.module.weather.ui.bean.Now
import com.zlyandroid.module.weather.ui.model.WeatherModel
import com.zlyandroid.module.weather.ui.view.CityListView
import com.zlyandroid.module.weather.ui.view.SearchResultView
import com.zlyandroid.module.weather.util.DataUtils
import com.zlyandroid.module.weather.util.JsonUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
class SearchResultPresenter : BasePresenter<SearchResultView>() {
    var mModel = WeatherModel()
    //城市列表
    private var mCityListExecutor: CityListExecutor? = null

    override fun attachView(view: SearchResultView) {
        super.attachView(view)

        mCityListExecutor = CityListExecutor()
    }
    override fun detachView() {
        super.detachView()
        mCityListExecutor?.destroy()
    }


    /**
     * 城市信息查询
     * @param location 需要查询的城市 汉字和拼音
     * @param isAdd 是否要添加数据库  如果在数据库没有查到该城市的数据，就需要添加到数据库方便下次查找
     * */
    fun lookup(location: String) {
        L.i(location)
        mModel.lookup(location, Constants.weatherKey)
            .go(mModel, mView, isShowLoading = false, onSuccess = {
                L.i(it.location)
                mView?.getSearchResultSuccess(it.location)

            }, onError = {
                L.i("没有东西")
                mView?.getSearchResultFailed()
            })
    }
    /**
     * 根据城市名字查询数据库
     * */
    fun getDBNow(cityName: String,cityNameId: String) {
        mCityListExecutor?.findByName(cityName, success = object : SimpleCallback<CityModel> {
            override fun onResult(data: CityModel) {
                L.i(data)
                data.cityId =cityNameId
                updataDBNow(data)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                //mView?.nowFailed()
               var cityModel = CityModel(cityNameId,cityName, null, true, "", System.currentTimeMillis(), System.currentTimeMillis() )
                addDBCity(cityModel)
            }
        })
    }

    /**
     * 更新数据库信息
     * */
    fun updataDBNow(model: CityModel) {
        mCityListExecutor?.updata(model, success = object : SimpleListener {
            override fun onResult() {
                LogCat(model)
            }

        }, error = object : SimpleListener {
            override fun onResult() {
            }
        })
    }

    /**
     * 添加到数据库
     * */
    fun addDBCity(model: CityModel) {
        mCityListExecutor?.add(model, success = object : SimpleCallback<List<CityModel>> {
            override fun onResult(data: List<CityModel>) {
                LogCat(model)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
            }
        })
    }


    /**
    * 查看一下更改后或者插入的数据
    * */

    fun LogCat(model: CityModel) {
        mCityListExecutor?.findByName(
            model.cityName,
            success = object : SimpleCallback<CityModel> {
                override fun onResult(data: CityModel) {
                    L.i(data)
                    mView?.getSuccess(data)
                }
            },
            error = object : SimpleListener {
                override fun onResult() {
                    //mView?.nowFailed()
                    L.i("没有？")
                    mView?.getFailed()
                }
            })
    }

}