package com.zlyandroid.apitoolkotlin.ui.mine.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.basic.common.util.log.L
/**
 * @author zhangliyang
 * @date 2020/12/24
 * GitHub: https://github.com/ZLYang110
 * desc:签到日记
 */
class SignAdapter(private val context: Context?, datas: List<DiaryModel>) : BaseQuickAdapter<DiaryModel, BaseViewHolder>(R.layout.item_signlog, datas){

    override fun convert(helper: BaseViewHolder, item: DiaryModel?) {
        item ?: return
        helper.setText(R.id.tv_time, item.data)
            .setText(R.id.tv_desc,item.content)
            .addOnClickListener(R.id.iv_mean)
    }
}