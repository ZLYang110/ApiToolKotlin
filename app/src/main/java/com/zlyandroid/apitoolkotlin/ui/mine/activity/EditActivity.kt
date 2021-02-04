package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.UserInfoManager
import com.zlyandroid.apitoolkotlin.db.executor.HobbyExecutor
import com.zlyandroid.apitoolkotlin.db.executor.SkillExecutor
import com.zlyandroid.apitoolkotlin.listener.MyTextWatcher
import com.zlyandroid.basic.common.base.BaseActivity
import com.zlyandroid.basic.common.listener.SimpleListener
import kotlinx.android.synthetic.main.activity_edit_name.*

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 * desc:修改名字
 */
class EditActivity : BaseActivity() {

    var code: Int = 0
    private var MAX_COUNT = 20

    private var mSkillExecutor: SkillExecutor? = null
    private var mHobbyExecutor: HobbyExecutor? = null

    companion object {
        private val REQUEST_CODE_UPDATA = "edit_uppata"
        val REQUEST_CODE_UPDATA_NAME = 1 //修改昵称
        val REQUEST_CODE_UPDATA_TRUENAME = 2 //修改姓名
        val REQUEST_CODE_UPDATA_POSITION = 3 //职位
        val REQUEST_CODE_UPDATA_COMPANY = 4 //公司
        val REQUEST_CODE_UPDATA_LABEL = 5 //技能
        val REQUEST_CODE_UPDATA_INDUSTRY = 6 //行业
        val REQUEST_CODE_UPDATA_INTEREST = 7 //兴趣


        fun start(context: Context, code: Int) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(REQUEST_CODE_UPDATA, code)
            context.startActivity(intent)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_edit_name

    override fun initView() {
        mSkillExecutor = SkillExecutor()
        mHobbyExecutor = HobbyExecutor()
        code = intent.getIntExtra(REQUEST_CODE_UPDATA, 0)
        UserInfoManager.getUserInfo()?.apply {
            when (code) {
                REQUEST_CODE_UPDATA_NAME -> {
                    abc.titleTextView.setText("昵称")
                    et_content.setText(userNickname)
                    text_count.setText(userNickname.length.toString() + "/" + MAX_COUNT)
                }
                REQUEST_CODE_UPDATA_TRUENAME -> {
                    abc.titleTextView.setText("姓名")
                    et_content.setText(userName)
                    text_count.setText(userName.length.toString() + "/" + MAX_COUNT)
                }
                REQUEST_CODE_UPDATA_POSITION -> {
                    abc.titleTextView.setText("职位")
                    et_content.setText(position)
                    text_count.setText(position.length.toString() + "/" + MAX_COUNT)
                }
                REQUEST_CODE_UPDATA_COMPANY -> {
                    abc.titleTextView.setText("公司")
                    et_content.setText(company)
                    text_count.setText(company.length.toString() + "/" + MAX_COUNT)
                }
                REQUEST_CODE_UPDATA_LABEL -> {
                    MAX_COUNT = 5;
                    abc.titleTextView.setText("技能")
                    et_content.setText("")
                    text_count.setText( "0/" + MAX_COUNT)
                }
                REQUEST_CODE_UPDATA_INDUSTRY -> {
                    MAX_COUNT = 5;
                    abc.titleTextView.setText("行业")
                    et_content.setText("")
                    text_count.setText( "0/" + MAX_COUNT)
                }


            }
        }
        et_content.addTextChangedListener(textWatcher)
    }

    private val textWatcher: MyTextWatcher = object : MyTextWatcher() {
        override fun afterMyTextChanged(editable: Editable) {
            text_count.setText((editable.length.toString() + "/" + MAX_COUNT))
            if (editable.length > 0) {
                iv_clear.visibility = View.VISIBLE
            }
        }
    }

    override fun initData() {
        abc.rightTextView.setOnClickListener(View.OnClickListener {
            var editContent = et_content.text.toString()
            when (code) {
                REQUEST_CODE_UPDATA_NAME -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入昵称", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        userNickname = editContent
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                }
                REQUEST_CODE_UPDATA_TRUENAME -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入姓名", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        userName = editContent
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                }

                REQUEST_CODE_UPDATA_POSITION -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入职位", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        position = editContent
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                }

                REQUEST_CODE_UPDATA_COMPANY -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入公司", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        company = editContent
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                }
                REQUEST_CODE_UPDATA_LABEL -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入技能", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        if(!labelList.contains(editContent)){
                            labelList.add(editContent)
                        }
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                    mSkillExecutor?.add( object : SimpleListener {
                        override fun onResult() {
                            // showToast("添加成功")
                        }
                    }, object : SimpleListener {
                        override fun onResult() {
                            //showToast("添加失败")
                        }
                    },editContent)

                }
                REQUEST_CODE_UPDATA_INDUSTRY -> {
                    if (TextUtils.isEmpty(editContent)) {
                        showToast("请输入行业", Gravity.CENTER)
                        return@OnClickListener
                    }
                    UserInfoManager.getUserInfo()?.apply {
                        industry = editContent
                        UserInfoManager.saveUserInfo(this@apply)
                    }
                    mHobbyExecutor?.add( object : SimpleListener {
                        override fun onResult() {
                            // showToast("添加成功")
                        }
                    }, object : SimpleListener {
                        override fun onResult() {
                            //showToast("添加失败")
                        }
                    },editContent)
                }
            }
            finish()
        })
        iv_clear.setOnClickListener(this)
    }

    override fun onClick2(v: View) {
        when (v.id) {
            R.id.iv_clear -> {
                et_content.text = null
                iv_clear.visibility = View.GONE
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mSkillExecutor?.destroy()
        mHobbyExecutor?.destroy()
    }
}