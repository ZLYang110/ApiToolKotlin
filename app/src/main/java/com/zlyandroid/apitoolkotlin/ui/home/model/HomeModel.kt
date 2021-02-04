package com.zlyandroid.apitoolkotlin.ui.home.model

import com.zlyandroid.apitoolkotlin.http.HttpResult
import com.zlyandroid.apitoolkotlin.http.LoginData
import com.zlyandroid.apitoolkotlin.http.RHttp
import com.zlyandroid.basic.common.mvp.BaseModel
import io.reactivex.Observable

/**
 * @author zhangliyang
 * @date 2020/11/30
 * GitHub: https://github.com/ZLYang110
 */
class HomeModel : BaseModel() {

      fun login(username: String, password: String): Observable<HttpResult<LoginData>> {
        return RHttp.getAPi().login(username, password)
    }

}