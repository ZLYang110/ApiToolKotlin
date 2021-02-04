package com.zlyandroid.apitoolkotlin.ui.mine.fragment

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.UserInfoManager
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.ui.mine.adapter.SignAdapter
import com.zlyandroid.apitoolkotlin.ui.mine.presenter.SignInPresenter
import com.zlyandroid.apitoolkotlin.ui.mine.view.SignInView
import com.zlyandroid.basic.common.base.BaseMvpFragment
import com.zlyandroid.basic.common.dialog.ListDialog
import com.zlyandroid.basic.common.util.log.L
import kotlinx.android.synthetic.main.activity_edit_userinfo.*
import kotlinx.android.synthetic.main.fragment_sign.*

/**
 * @author zhangliyang
 * @date 2020/12/24
 * GitHub: https://github.com/ZLYang110
 * desc:签到日记
 */
class SignFragment  : BaseMvpFragment<SignInView, SignInPresenter>(), SignInView {

    /**
     * datas
     */
    private var datas : MutableList<DiaryModel>  =  ArrayList()

    private val mAdapter: SignAdapter by lazy {
        SignAdapter(activity, datas = datas)
    }

    companion object {
        fun getInstance(): SignFragment = SignFragment()
    }
    override fun createPresenter(): SignInPresenter = SignInPresenter()

    override fun getLayoutID(): Int = R.layout.fragment_sign

    override fun initView() {
        rv.run {
            layoutManager = LinearLayoutManager(context)
            adapter =mAdapter
        }
        mAdapter.run {
            //bindToRecyclerView(rv)
            onItemClickListener = this@SignFragment.onItemClickListener
            onItemChildClickListener = this@SignFragment.onItemChildClickListener
        }
    }

    override fun initData() {
        mPresenter?.getList()
    }
    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {

        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (datas.size != 0) {
              val item= mAdapter.getItem(position)
                when (view.id) {
                    R.id.iv_mean -> {
                        ListDialog.with(context)
                            .cancelable(true)
                            .noYseBtn()
                            .datas("删除" )
                            .currSelectPos(0)
                            .listener(object : ListDialog.OnItemSelectedListener {
                                override fun onSelect(data: String, pos: Int) {
                                    mAdapter.remove(position)
                                    if (item != null) {
                                        mPresenter?.removeById(item.id)
                                    }

                                }
                            })
                            .show()

                    }
                }
            }
        }


    override fun diaryListSuccess(data: List<DiaryModel>) {
        L.i(data)
        datas =   ArrayList()
        data.forEach {
            L.i(it.content)
            if(!TextUtils.isEmpty(it.content)){
                L.i("-------------------")
                datas.add(it)
            }
        }
        L.i(datas)
        mAdapter.setNewData(datas)

    }

    override fun diaryListFailed() {

    }


    override fun toDaySignSuccess() {
        TODO("Not yet implemented")
    }

    override fun toDaySignFailed() {
        TODO("Not yet implemented")
    }
    override fun diarySuccess(data: DiaryModel) {
        TODO("Not yet implemented")
    }

    override fun diaryFailed() {
        TODO("Not yet implemented")
    }

    override fun removeSuccess() {
         showToast("删除成功")
    }

    override fun removeFailed() {
        showToast("删除失败")
        mAdapter.setNewData(datas)
    }


}