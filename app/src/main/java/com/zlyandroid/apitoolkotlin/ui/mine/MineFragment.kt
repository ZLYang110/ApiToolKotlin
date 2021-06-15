package com.zlyandroid.apitoolkotlin.ui.mine

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.SmartUtil
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.ui.mine.activity.SettingActivity
import com.zlyandroid.apitoolkotlin.ui.mine.activity.SignInActivity
import com.zlyandroid.apitoolkotlin.ui.mine.activity.SpaceActivity
import com.zlyandroid.apitoolkotlin.ui.mine.activity.WalletActivity
import com.zlyandroid.basic.common.base.BaseFragment
import com.zlyandroid.basic.common.util.SmartRefreshUtils
import com.zlyandroid.basic.common.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.view_mine.*

/**
 * @author zhangliyang
 * @date 2020/11/16
 * GitHub: https://github.com/ZLYang110
 * desc:我的
 */
class MineFragment  : BaseFragment() {


    private lateinit var mSmartRefreshUtils: SmartRefreshUtils
    private val mOffset = 0
    private var mScrollY = 0

    companion object {
        fun getInstance(): MineFragment = MineFragment()
    }

    override fun getLayoutID(): Int = R.layout.fragment_mine

    override fun initView() {

        //状态栏透明和间距处理
        StatusBarUtil.immersive(context)
      //  StatusBarUtil.setPaddingSmart(context, abc)
        StatusBarUtil.setMargin(context, header)

        //禁止上拉加载
        mSmartRefreshUtils = SmartRefreshUtils.with(srl)
        mSmartRefreshUtils.pureScrollMode()
       /* mSmartRefreshUtils.setRefreshListener(object : SmartRefreshUtils.RefreshListener {
            override fun onRefresh() {
                srl.finishRefresh(1000)
            }
        })*/

        abc.rightIconView.setOnClickListener { SettingActivity.start(context) }

        srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            @SuppressLint("Range")
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                //设置title渐变效果
                abc.setAlpha(1 - Math.min(percent, 1f))

            }

        })

        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = SmartUtil.dp2px(100f)
            private val color: Int =
                ContextCompat.getColor(context, R.color.colorPrimary) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                var scrollY = scrollY
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY)
                    mScrollY = if (scrollY > h) h else scrollY
                    abc.background.setAlpha((1f * mScrollY / h).toInt())
                    //abc.setAlpha(1f * mScrollY / h)
                    abc.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = mOffset - mScrollY.toFloat()
                    abc.titleTextView.setText("")
                }else{
                    abc.titleTextView.setText("我的")
                }
                lastScrollY = scrollY
            }
        })
    }

    override fun initData() {
        tv_user_space.setOnClickListener(this)
        ll_collection.setOnClickListener(this)
        ll_history.setOnClickListener(this)
        ll_signin.setOnClickListener(this)
        ll_wallet.setOnClickListener(this)
    }

    override fun onClick2(v: View) {
        super.onClick2(v)
        when(v.id){
            R.id.tv_user_space -> {
                SpaceActivity.start(context)
            }
            R.id.ll_collection -> {
                showToast("待开发")
            }
            R.id.ll_history -> {
                showToast("待开发")
            }
            R.id.ll_signin -> {
                SignInActivity.start(context)
            }
            R.id.ll_wallet -> {
                WalletActivity.start(context)
            }
        }
    }



}