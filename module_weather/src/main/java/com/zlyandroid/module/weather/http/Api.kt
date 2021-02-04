package com.zlyandroid.module.weather.http

import com.zlyandroid.module.weather.http.response.ResponseBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author zhangliyang
 * @date 2020/12/01
 * GitHub: https://github.com/ZLYang110
 *  desc:
 */
interface Api {

    /**
     * 现在的天气
     */

    @GET("v7/weather/now")
    fun nowWeather(@Query("location") location: String, @Query("key") key: String)
            : Observable<HttpResult<Now>>

    /**
     * 现在的天气
     */

    @GET("v7/weather/24h")
    fun query24h(@Query("location") location: String, @Query("key") key: String)
            : Observable<HttpResult<MutableList<Hourly>>>
    /**
     * 7天天气
     */

    @GET("v7/weather/7d")
    fun query7d(@Query("location") location: String, @Query("key") key: String)
            : Observable<HttpResult<MutableList<Daily>>>

    /**
     * 空气质量
     */

    @GET("v7/air/now")
    fun nowAir(@Query("location") location: String, @Query("key") key: String)
            : Observable<HttpResult<Air>>
}