package com.zlyandroid.apitoolkotlin.http

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author zhangliyang
 * @date 2020/12/01
 * GitHub: https://github.com/ZLYang110
 *  desc:
 */
interface Api {

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String)
            : Observable<HttpResult<LoginData>>
}