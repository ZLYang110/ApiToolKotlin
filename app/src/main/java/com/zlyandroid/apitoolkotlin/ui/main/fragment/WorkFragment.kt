package com.zlyandroid.apitoolkotlin.ui.main.fragment

import android.content.Context
import android.content.Intent
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.basic.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author zhangliyang
 * @date 2020/11/16
 * GitHub: https://github.com/ZLYang110
 * desc:发现
 */
class WorkFragment  : BaseFragment() {

    companion object {
        fun getInstance(): WorkFragment = WorkFragment()
    }

    override fun getLayoutID(): Int = R.layout.fragment_work

    override fun initView() {
    }

    override fun initData() {
    }

}