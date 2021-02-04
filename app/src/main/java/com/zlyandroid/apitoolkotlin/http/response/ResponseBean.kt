package com.zlyandroid.apitoolkotlin.http.response

import android.text.TextUtils

open class ResponseBean {
    var errorCode: Int = 0
    var errorMsg: String = ""

    fun getRCode():Int{
        if(errorCode != 0){
            return errorCode
        }
        return errorCode
    }
    fun getRMsg():String{
        if(TextUtils.isEmpty(errorMsg)){
            return "失败"
        }
        if (!errorMsg.isNotEmpty())
            return errorMsg
        return "成功"
    }
}