package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.draggable.library.extension.ImageViewerHelper
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.UserInfoManager
import com.zlyandroid.apitoolkotlin.ui.main.adapter.CommPagerAdapter
import com.zlyandroid.apitoolkotlin.ui.main.fragment.WorkFragment
import com.zlyandroid.apitoolkotlin.ui.mine.fragment.SignFragment
import com.zlyandroid.apitoolkotlin.util.DisplayUtil
import com.zlyandroid.apitoolkotlin.util.ScreenUtil
import com.zlyandroid.apitoolkotlin.widget.ColorFlipPagerTitleView
import com.zlyandroid.basic.common.base.BaseActivity
import com.zlyandroid.basic.common.util.SmartRefreshUtils
import com.zlyandroid.basic.common.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_space.*
import kotlinx.android.synthetic.main.view_personal_header.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import java.util.*

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 * desc:个人空间
 */
class SpaceActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener {

    private var mFragments = ArrayList<Fragment>()
    private var mViewPagerAdapter: CommPagerAdapter? = null
    private lateinit var mCommonNavigator: CommonNavigator

    private val mTitles = arrayOf("文章", "签到", "其他")
    private val mTitleList = Arrays.asList(*mTitles)

    private lateinit var mLabelAdapter: BaseQuickAdapter<String, BaseViewHolder>//标签
    private lateinit var mHobbyAdapter: BaseQuickAdapter<String, BaseViewHolder>//兴趣


    private lateinit var mSmartRefreshUtils: SmartRefreshUtils
    private var mScreenWidth = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SpaceActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun initBeforeInfo() {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(getActivity())
    }
    override fun getLayoutID(): Int = R.layout.activity_space

    override fun initView() {
        //获得屏幕宽度
        mScreenWidth = ScreenUtil.getScreenWidth(getActivity())
        StatusBarUtil.setMargin(getActivity(), header)
        //增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)  titleBar
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar)
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar1)
        appbarlayout.addOnOffsetChangedListener(this)
        //禁止上拉加载
        mSmartRefreshUtils = SmartRefreshUtils.with(srl)
        mSmartRefreshUtils.pureScrollMode()
       /* mSmartRefreshUtils.setRefreshListener(object : SmartRefreshUtils.RefreshListener {
            override fun onRefresh() {
                srl.finishRefresh(1000)
            }
        })*/

        srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {

                //设置图片向下移动
                parallax.setTranslationY(offset / 2.toFloat())
                //设置title渐变效果
                toolbar1.setAlpha(1 - Math.min(percent, 1f))
                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
                if (offset <= 100) {
                    val layoutParams: ViewGroup.LayoutParams = parallax.getLayoutParams()
                    layoutParams.width = mScreenWidth + offset
                    (layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                        -(layoutParams.width - mScreenWidth) / 2,
                        -DisplayUtil.dip2px(getActivity(), 50f),
                        0,
                        0
                    )
                    parallax.requestLayout()
                }
            }

        })

    }

    override fun initData() {
        initFragment()
        initMagicIndicator()
        mViewPagerAdapter = CommPagerAdapter(
            supportFragmentManager,
            mFragments,
            mTitleList.toTypedArray()
        )
        viewpager.setAdapter(mViewPagerAdapter)

        initUserInfo()
        tv_editorUserinfo.setOnClickListener(this)
        iv_head.setOnClickListener(this)
    }
    override fun onClick2(view: View) {
        when (view.id) {
            R.id.iv_head -> {
                UserInfoManager.getUserInfo()?.headImg?.let {
                    ImageViewerHelper.showSimpleImage(
                        getActivity(),
                        it,
                        view = iv_head,
                        showDownLoadBtn = false
                    )
                }
            }
            R.id.tv_editorUserinfo -> {
                UserInfoActivity.start(getActivity())
            }
        }
    }
    //初始化数据
    fun initUserInfo(){
        toolbar1.background.alpha=1
        tv_title.alpha= 1f
        UserInfoManager.getUserInfo()?.let {
            tv_title.setText(it.userNickname)
            tv_username.setText(it.userNickname)
            tv_age.setText(it.age.toString())
            initlabel(it.labelList)
            tv_githubUrl.setText(it.githubUrl)
            tv_sign.setText(it.autograph)
            inithobby(it.interestList)
            if(!TextUtils.isEmpty(it.headImg)){
                Glide.with(getActivity()).load(it).into(iv_head)
            }else{
                iv_head.setImageResource(R.mipmap.avatar)
            }
        }
    }
    //标签
    fun initlabel(data: List<String>) {
        rv_label.setNestedScrollingEnabled(false)
        rv_label.setHasFixedSize(true)
        rv_label.setLayoutManager(FlexboxLayoutManager(getActivity()))
        mLabelAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_label) {
                override fun convert(helper: BaseViewHolder, item: String) {
                    helper.setText(R.id.tv_name, item)
                }
            }
        rv_label.setAdapter(mLabelAdapter)
        mLabelAdapter.setNewData(data)
    }

    //兴趣
    fun inithobby(data: List<String>) {
        rv_hobby.setNestedScrollingEnabled(false)
        rv_hobby.setHasFixedSize(true)
        rv_hobby.setLayoutManager(FlexboxLayoutManager(getActivity()))
        mHobbyAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hobby) {
                override fun convert(helper: BaseViewHolder, item: String) {
                    helper.setText(R.id.tv_name, item)
                }
            }
        rv_hobby.setAdapter(mHobbyAdapter)
        mHobbyAdapter.setNewData(data)
    }
    private fun initFragment() {
        mFragments = ArrayList()
        mFragments.add(WorkFragment.getInstance())
        mFragments.add(SignFragment.getInstance())
        mFragments.add(WorkFragment.getInstance())
    }

    private fun initMagicIndicator() {
        magic_indicator.setBackgroundColor(resources.getColor(R.color.surface))
        mCommonNavigator = CommonNavigator(getActivity())
        mCommonNavigator.setAdjustMode(true)
        mCommonNavigator.setAdapter(object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mTitleList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView: SimplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.setText(mTitleList.get(index))
                simplePagerTitleView.textSize = 14f
                simplePagerTitleView.normalColor = Color.BLACK
                simplePagerTitleView.selectedColor = resources.getColor(R.color.main)
                simplePagerTitleView.normalColor = resources.getColor(R.color.text_surface)
                simplePagerTitleView.setOnClickListener { viewpager.setCurrentItem(index) }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = UIUtil.dip2px(context, 2.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 30.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(resources.getColor(R.color.main))
                return indicator
            }
        })
        magic_indicator.setNavigator(mCommonNavigator)
        ViewPagerHelper.bind(magic_indicator, viewpager)
    }


    override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
        val scrollRangle: Int = appbarlayout.getTotalScrollRange()

        /**
         * 如果是verticalOffset改成负数   有不一样的效果，可以模拟试试
         */
         parallax.setTranslationY(verticalOffset.toFloat())

        /**
         * 这个数值可以自己定义
         */
     /*   if (verticalOffset < -10) {
            iv_menu.setImageResource(R.mipmap.icon_menu_black)
        } else {
            iv_menu.setImageResource(R.mipmap.icon_menu_white)
        }*/

        val mAlpha = Math.abs(255f / scrollRangle * verticalOffset).toInt()
        toolbar1.background.alpha=mAlpha
        tv_title.alpha= mAlpha.toFloat()
        //顶部title渐变效果
        //toolbar1.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255))
        //tv_title.setTextColor(Color.argb(mAlpha, 0, 0, 0))
    }

    override fun onResume() {
        super.onResume()
        initUserInfo()
    }
}