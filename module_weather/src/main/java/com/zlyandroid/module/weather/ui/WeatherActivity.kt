package com.zlyandroid.module.weather.ui

import android.graphics.Color
import android.os.Build
import android.view.WindowManager
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.zlyandroid.basic.common.base.BaseActivity
import com.zlyandroid.basic.common.base.BaseMvpActivity
import com.zlyandroid.basic.common.config.ARouterConfig
import com.zlyandroid.basic.common.util.SmartRefreshUtils
import com.zlyandroid.basic.common.util.StatusBarUtil
import com.zlyandroid.basic.common.util.log.L
import com.zlyandroid.module.weather.R
import com.zlyandroid.module.weather.http.Daily
import com.zlyandroid.module.weather.http.Hourly
import com.zlyandroid.module.weather.http.Now
import com.zlyandroid.module.weather.ui.adapter.D24hAdapter
import com.zlyandroid.module.weather.ui.adapter.DayAdapter
import com.zlyandroid.module.weather.ui.presenter.WeatherPresenter
import com.zlyandroid.module.weather.ui.view.WeatherView
import com.zlyandroid.module.weather.util.DateUtil
import com.zlyandroid.module.weather.widget.weather.BaseWeather
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.activity_weather.ctl_main
import kotlinx.android.synthetic.main.activity_weather.toolbar1
import kotlinx.android.synthetic.main.view_weather_content.*

/**
 * @author zhangliyang
 * @date 2021/1/09
 * GitHub: https://github.com/ZLYang110
 * desc:天气
 */

@Route(path = ARouterConfig.WEATHER_ACTIVITY_MAIN)
class WeatherActivity : BaseMvpActivity<WeatherView, WeatherPresenter>(), WeatherView, AppBarLayout.OnOffsetChangedListener  {


    private lateinit var mSmartRefreshUtils: SmartRefreshUtils
    private var mD24hdatas : MutableList<Hourly>  =  ArrayList()

    private val mD24hAdapter: D24hAdapter by lazy {
        D24hAdapter(ArrayList())
    }
    private val mDayAdapter: DayAdapter by lazy {
        DayAdapter(ArrayList())
    }

    override fun swipeBackEnable(): Boolean  =false
    override fun createPresenter(): WeatherPresenter = WeatherPresenter()
    override fun initBeforeInfo() {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(getActivity())
    }
    override fun getLayoutID(): Int  =  R.layout.activity_weather
    override fun initView() {
        //StatusBarUtil.setMargin(getActivity(), header)
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar)
        StatusBarUtil.setPaddingSmart(getActivity(), toolbar1)
        weatherView.setWeatherType(BaseWeather.Type.SUNNY)
        ctl_main.setTitle("北京")
        ctl_main.setCollapsedTitleTextColor(Color.WHITE)
        ctl_main.setExpandedTitleColor(Color.WHITE)
      /*  mSmartRefreshUtils = SmartRefreshUtils.with(srl)
        mSmartRefreshUtils.pureScrollMode()
        mSmartRefreshUtils.setRefreshListener(object : SmartRefreshUtils.RefreshListener {
            override fun onRefresh() {
                mPresenter?.now("101010100")
            }
        })*/
        abl_main.addOnOffsetChangedListener(this)
        rv_24h.run {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter =mD24hAdapter
        }
        rv_7d.run {
            layoutManager = LinearLayoutManager(context)
            adapter =mDayAdapter
        }
    }

    override fun initData() {
        mPresenter?.now("101010100")
        mPresenter?.query24h("101010100")
        mPresenter?.query7d("101010100")

    }

    override fun nowSuccess(data: Now,updataTime:String) {
        tv_updataTime.setText("上次更新时间:"+DateUtil.strToDateHHmm(updataTime))
        tv_temp.setText(data.temp)
        tv_text.setText(data.text)
    }

    override fun nowFailed() {
    }

    override fun query24hSuccess(data: MutableList<Hourly>) {
        mD24hAdapter.setNewData(data)
       // rv_24h.scrollToPosition(6)
    }

    override fun query24hFailed() {
    }

    override fun query7dSuccess(data: MutableList<Daily>) {
        mDayAdapter.setNewData(data)
    }

    override fun query7dFailed() {
    }
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        var Offset =  Math.abs(verticalOffset)
        val mAlpha = 1 - Offset.toFloat() / 210f
        ll_now.alpha=mAlpha
      //  L.i(""+Offset +"====="+mAlpha)



    }
    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}