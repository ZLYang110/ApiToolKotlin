package com.zlyandroid.apitoolkotlin.ui.mine.dialog

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.zlyandroid.apitoolkotlin.R
import com.zlylib.upperdialog.Upper
import com.zlylib.upperdialog.common.AnimatorHelper
import com.zlylib.upperdialog.manager.Layer
import com.zlylib.upperdialog.manager.Layer.AnimatorCreator
import java.util.*


/**
 * @author zhangliyang
 * @date 2020/11/27
 * GitHub: https://github.com/ZLYang110
 *  desc: 标签选择
 */

class SelectLabelDialog(mContext: Context) {
    private var context: Context
    private var allDatas: MutableList<String> = ArrayList()
    private var selectDatas: MutableList<String> = ArrayList()
    private var selectData: String? = null
    private var listener: OnItemSelectedListener? = null

    /**
     * 只能选择一个
     * 如果是true 需传值 selectData
     * 如果是false 需传值 selectDatas
    * */
    private var singleSelelct = false




    init {
        context = mContext
    }

    companion object {
        fun with(context: Context): SelectLabelDialog {
            return SelectLabelDialog(context)
        }
    }

    fun singleSelelct(): SelectLabelDialog {
        singleSelelct = true
        return this
    }
    fun allDatas(datas: List<String>): SelectLabelDialog {
        this.allDatas = ArrayList()
        this.allDatas.addAll(datas)
        return this
    }

    fun allDatas(vararg datas: String): SelectLabelDialog {
        return allDatas(Arrays.asList(*datas))
    }

    fun selectDatas(datas: List<String>): SelectLabelDialog {
        this.selectDatas = ArrayList()
        this.selectDatas.addAll(datas)
        return this
    }

    fun selectDatas(vararg datas: String): SelectLabelDialog {
        return selectDatas(Arrays.asList(*datas))
    }
    fun selectData(selectData: String): SelectLabelDialog {
        this.selectData = selectData
        return this
    }
    fun listener(listener: OnItemSelectedListener): SelectLabelDialog {
        this.listener = listener
        return this
    }

    fun show() {
        Upper.dialog(context)
            .contentView(R.layout.dialog_lable)
            .backgroundDimDefault()
            .cancelableOnTouchOutside(true)
            .cancelableOnClickKeyBack(true)
            .contentAnimator(object : AnimatorCreator {
                override fun createInAnimator(target: View): Animator {
                    val cv: View = target.findViewById(R.id.dialog_cv_label)
                    val bar: View = target.findViewById(R.id.dialog_rl_bottom_bar)
                    bar.translationY = 1000f

                    val barAnim = AnimatorHelper.createBottomInAnim(bar)
                    barAnim.duration = 300
                    barAnim.startDelay = 150
                    val vpAlpha = AnimatorHelper.createTopInAnim(cv)
                    vpAlpha.startDelay = 0
                    vpAlpha.duration = 300
                    val set = AnimatorSet()
                    set.playTogether(vpAlpha, barAnim)
                    return set
                }

                override fun createOutAnimator(target: View): Animator {
                    val cv: View = target.findViewById(R.id.dialog_cv_label)
                    val bar: View = target.findViewById(R.id.dialog_rl_bottom_bar)
                    val barAnim = AnimatorHelper.createBottomOutAnim(bar)
                    barAnim.duration = 300
                    barAnim.startDelay = 0
                    val vpAlpha = AnimatorHelper.createTopOutAnim(cv)
                    vpAlpha.startDelay = 150
                    vpAlpha.duration = 300
                    val set = AnimatorSet()
                    set.playTogether(vpAlpha, barAnim)
                    return set
                }
            })
            .onClickToDismiss(object : Layer.OnClickListener{
                override fun onClick(layer: Layer?, v: View?) {
                    listener?.onSelect(selectDatas, -3)//自定义
                }

            },R.id.tv_custom)
            .onClickToDismiss(R.id.dialog_iv_close)
            .bindData(Layer.DataBinder { layer ->
                val rv_label = layer.getView<RecyclerView>(R.id.rv_label)
                rv_label.setNestedScrollingEnabled(false)
                rv_label.setHasFixedSize(true)
                rv_label.setLayoutManager(FlexboxLayoutManager(context))
                var mLabelAdapter =
                    object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_label_big) {
                        override fun convert(helper: BaseViewHolder, item: String) {
                            helper.setText(R.id.tv_name, item)
                            if (singleSelelct) {
                                if (selectData.equals(item)) {
                                    helper.setBackgroundRes(
                                        R.id.tv_name,
                                        R.drawable.btn_radius_blue_radian_bg
                                    )
                                } else {
                                    helper.setBackgroundRes(
                                        R.id.tv_name,
                                        R.drawable.btn_radius_grey_radian_bg
                                    )
                                }
                            } else {
                                if (selectDatas.contains(item)) {
                                    helper.setBackgroundRes(
                                        R.id.tv_name,
                                        R.drawable.btn_radius_blue_radian_bg
                                    )
                                } else {
                                    helper.setBackgroundRes(
                                        R.id.tv_name,
                                        R.drawable.btn_radius_grey_radian_bg
                                    )
                                }
                            }

                        }
                    }
                mLabelAdapter.setOnItemClickListener { adapter, view, position ->
                    mLabelAdapter.getItem(position)?.let {
                        if (singleSelelct) {
                            if (!selectData.equals(it)) {
                                selectData = it
                                mLabelAdapter.notifyDataSetChanged()
                            }
                        } else {
                            if (selectDatas.contains(it)) {
                                selectDatas.remove(it)
                            } else {
                                selectDatas.add(it)
                            }
                            mLabelAdapter.notifyDataSetChanged()
                        }
                    }
                }
                rv_label.setAdapter(mLabelAdapter)
                mLabelAdapter.setNewData(allDatas)
            }).onDismissListener(object : Layer.OnDismissListener {
                override fun onDismissing(layer: Layer?) {

                }

                override fun onDismissed(layer: Layer?) {
                    listener?.apply {
                        if (singleSelelct) {
                            selectDatas = ArrayList()
                            selectData?.let { selectDatas.add(it) }

                        }
                        onSelect(selectDatas, 0)

                    }
                }

            })
            .show()

    }

    interface OnItemSelectedListener {
        fun onSelect(datas: List<String>, pos: Int)
    }
}