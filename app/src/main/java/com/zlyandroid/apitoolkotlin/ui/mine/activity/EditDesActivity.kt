package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.UserInfoManager
import com.zlyandroid.apitoolkotlin.listener.MyTextWatcher
import com.zlyandroid.basic.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_des.*
import kotlinx.android.synthetic.main.activity_edit_name.abc
import kotlinx.android.synthetic.main.activity_edit_name.text_count

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 * desc:修改名字
 */
class EditDesActivity : BaseActivity() {

    private val MAX_COUNT = 200

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EditDesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_edit_des

    override fun initView() {
        UserInfoManager.getUserInfo()?.apply {
            et_content.setText(autograph)
            text_count.setText(autograph.length.toString() + "/" + MAX_COUNT)
        }
        et_content.addTextChangedListener(textWatcher)
    }
    
    private val textWatcher: MyTextWatcher = object : MyTextWatcher() {
        override fun afterMyTextChanged(editable: Editable) {
            text_count.setText((editable.length.toString() + "/" + MAX_COUNT))
        }
    }

    override fun initData() {
        abc.rightTextView.setOnClickListener(View.OnClickListener {
            var editContent = et_content.text.toString()
            if (TextUtils.isEmpty(editContent)) {
                showToast("请输入...")
                return@OnClickListener
            }
            UserInfoManager.getUserInfo()?.apply {
                autograph = editContent
                UserInfoManager.saveUserInfo(this@apply)
            }
            finish()
        })
    }


}