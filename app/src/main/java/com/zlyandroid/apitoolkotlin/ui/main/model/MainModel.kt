package com.zlyandroid.apitoolkotlin.ui.main.model

import androidx.annotation.IntRange
import com.zlyandroid.basic.common.mvp.BaseModel

/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
class MainModel : BaseModel() {

    fun getQuestionListCache(@IntRange(from = 1) page: Int) {
    }
}