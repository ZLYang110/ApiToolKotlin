package com.zlyandroid.apitoolkotlin.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * @author zhangliyang
 * @date 2020/11/26
 * GitHub: https://github.com/ZLYang110
 * desc:
 */
abstract class MyTextWatcher : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

    override fun afterTextChanged(editable: Editable ) {
        afterMyTextChanged(editable)
    }

    abstract fun afterMyTextChanged(editable: Editable )
}