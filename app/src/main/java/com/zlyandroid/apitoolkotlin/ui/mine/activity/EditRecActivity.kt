package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.db.executor.DiaryExecutor
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.listener.MyTextWatcher
import com.zlyandroid.basic.common.base.BaseActivity
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import kotlinx.android.synthetic.main.activity_edit_des.*
import kotlinx.android.synthetic.main.activity_edit_name.abc
import kotlinx.android.synthetic.main.activity_edit_name.text_count

/**
 * @author zhangliyang
 * @date 2020/12/21
 * GitHub: https://github.com/ZLYang110
 * desc:日志
 */
class EditRecActivity : BaseActivity() {

    private val MAX_COUNT = 500

    private var mDiaryExecutor: DiaryExecutor? = null

    //传参
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0

    companion object {

        fun start(context: Activity ,mYear:Int,mMonth:Int,mDay:Int) {
            val intent = Intent(context, EditRecActivity::class.java)
            intent.putExtra("mYear",mYear)
            intent.putExtra("mMonth",mMonth)
            intent.putExtra("mDay",mDay)
            context.startActivityForResult(intent,1)
        }
    }

    override fun getLayoutID(): Int = R.layout.activity_edit_record

    override fun initView() {
        mDiaryExecutor = DiaryExecutor()
        mYear = intent.getIntExtra("mYear",0)
        mMonth = intent.getIntExtra("mMonth",0)
        mDay = intent.getIntExtra("mDay",0)
        et_content.addTextChangedListener(textWatcher)
    }

    private val textWatcher: MyTextWatcher = object : MyTextWatcher() {
        override fun afterMyTextChanged(editable: Editable) {
            text_count.setText((editable.length.toString() + "/" + MAX_COUNT))
        }
    }

    override fun initData() {
        abc.titleTextView.setText(mYear.toString()+"-"+mMonth.toString()+"-"+mDay.toString())
        mDiaryExecutor?.findById(mYear,mMonth,mDay,object : SimpleCallback<DiaryModel> {
            override fun onResult(data: DiaryModel) {
                et_content.setText(data.content)
            }
        }, object : SimpleListener {
            override fun onResult() {

            }
        })
        abc.rightTextView.setOnClickListener(View.OnClickListener {
            var editContent = et_content.text.toString()
            if (TextUtils.isEmpty(editContent)) {
                showToast("请输入...")
               return@OnClickListener
            }

            mDiaryExecutor?.add(mYear,mMonth,mDay,"记",editContent,object : SimpleListener {
                override fun onResult() {
                    showToast("添加成功")
                    val data = Intent()
                    setResult(1, data)
                    finish()
                }
            }, object : SimpleListener {
                override fun onResult() {
                    showToast("添加失败")
                }
            })
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        mDiaryExecutor?.destroy()
    }

}