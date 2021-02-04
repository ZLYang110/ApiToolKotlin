package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.basic.common.base.BaseActivity
/**
 * @author zhangliyang
 * @date 2020/11/17
 * GitHub: https://github.com/ZLYang110
 * desc:钱包
 */
class WalletActivity : BaseActivity() {

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, WalletActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_wallet

    override fun initView() {
    }

    override fun initData() {
    }
}