package com.zlyandroid.apitoolkotlin.ui.project

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.ui.mine.activity.UserInfoActivity
import com.zlyandroid.basic.common.base.BaseFragment
import com.zlyandroid.basic.common.config.ARouterConfig
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_project.*


/**
 * @author zhangliyang
 * @date 2020/12/07
 * GitHub: https://github.com/ZLYang110
 * desc:项目
 */
class ProjectFragment : BaseFragment() {

    companion object {
        fun getInstance(): ProjectFragment = ProjectFragment()
    }

    override fun getLayoutID(): Int = R.layout.fragment_project

    override fun initView() {
        tv_weather.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick2(v: View) {
        when(v.id){
            R.id.tv_weather -> {
                ARouter.getInstance().build(ARouterConfig.WEATHER_ACTIVITY_MAIN)
                    .navigation()
            }
        }
    }

}