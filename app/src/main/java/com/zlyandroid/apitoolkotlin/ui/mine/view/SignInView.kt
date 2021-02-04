package com.zlyandroid.apitoolkotlin.ui.mine.view

import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.basic.common.mvp.BaseView


/**
 * @author zhangliyang
 * @date 2020/11/18
 * GitHub: https://github.com/ZLYang110
 */
interface SignInView : BaseView {

    fun toDaySignSuccess()
    fun toDaySignFailed()

    fun diaryListSuccess(data: List<DiaryModel>)
    fun diaryListFailed()

    fun diarySuccess(data: DiaryModel)
    fun diaryFailed()

    fun removeSuccess( )
    fun removeFailed()
}