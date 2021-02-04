package com.zlyandroid.module.weather.ui.model

import com.zlyandroid.basic.common.mvp.BaseModel
import com.zlyandroid.module.weather.http.*
import com.zlyandroid.module.weather.http.response.ResponseBean
import io.reactivex.Observable

/**
 * @author zhangliyang
 * @date 2020/11/30
 * GitHub: https://github.com/ZLYang110
 */
class WeatherModel : BaseModel() {

    //实况天气
    fun nowWeather(location: String, key: String): Observable<HttpResult<Now>> {
        return RHttp.getAPi().nowWeather(location, key)
    }
    //逐小时预报（未来24小时）
    fun query24h(location: String, key: String): Observable<HttpResult<MutableList<Hourly>>> {
        return RHttp.getAPi().query24h(location, key)
    }

    //7天预报
    fun query7d(location: String, key: String): Observable<HttpResult<MutableList<Daily>>> {
        return RHttp.getAPi().query7d(location, key)
    }

    //空气质量
    fun nowAir(location: String, key: String): Observable<HttpResult<Air>> {
        return RHttp.getAPi().nowAir(location, key)
    }
}