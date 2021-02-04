package com.zlyandroid.apitoolkotlin.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import com.zlyandroid.apitoolkotlin.R

/**
 * @author zhangliyang
 * @date 2017/12/12
 * GitHub: https://github.com/ZLYang110
 * desc: 卡包
 */
class CardViewScllorView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView)

        typedArray.recycle()
    }


}