package com.zlyandroid.apitoolkotlin.ui.mine.dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.zlyandroid.basic.common.listener.SimpleCallback
import java.util.*

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 *  desc: 时间选择器
 */
object SelectTimeDialog {
    lateinit var pvTime: TimePickerView
    fun show(context: Context, date: Date, callback: SimpleCallback<Date>) {

        //Dialog 模式下，在底部弹出
        val selectedDate = Calendar.getInstance() //系统当前时间
        selectedDate.time = date
        val startDate = Calendar.getInstance()
        startDate.set(1990, 0, 1)
        val endDate = Calendar.getInstance()

        pvTime = TimePickerBuilder(context,
            OnTimeSelectListener { date, v ->
                Log.i("pvTime", "onTimeSelect")
                callback.onResult(date)
            })
            .setTimeSelectChangeListener {
                Log.i("pvTime", "onTimeSelectChanged")
            }
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
            .setRangDate(startDate, endDate)
            .addOnCancelClickListener {
                Log.i("pvTime", "onCancelClickListener")
            }
            .setItemVisibleCount(7) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
            .setLineSpacingMultiplier(2.0f)
            .setDividerColor(-0xdb5263)//设置分割线的颜色
            .isAlphaGradient(true)
            .build()

        val mDialog: Dialog = pvTime.getDialog()
        if (mDialog != null) {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.getDialogContainerLayout().setLayoutParams(params)
            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim) //修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f)
            }
        }
        pvTime.show()
    }



}