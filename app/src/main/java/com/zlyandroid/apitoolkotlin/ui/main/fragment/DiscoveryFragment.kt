package com.zlyandroid.apitoolkotlin.ui.main.fragment

import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.basic.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * @author zhangliyang
 * @date 2020/11/16
 * GitHub: https://github.com/ZLYang110
 * desc:分类
 */
class DiscoveryFragment : BaseFragment() {

    companion object {
        fun getInstance(): DiscoveryFragment = DiscoveryFragment()
    }

    override fun getLayoutID(): Int = R.layout.fragment_home

    override fun initView() {
    }

    override fun initData() {
    }
}