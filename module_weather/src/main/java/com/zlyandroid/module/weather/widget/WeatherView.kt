package com.zlyandroid.module.weather.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.zlyandroid.basic.common.util.log.L
import com.zlyandroid.module.weather.widget.weather.BaseWeather
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * @author zhangliyang
 * @date 2021/1/12
 * GitHub: https://github.com/ZLYang110
 * desc:天气背景自定义
 */

class WeatherView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var preDrawer: BaseWeather
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var isSwitchWeather=false

    init {
        preDrawer =
            BaseWeather.makeDrawerByType(
                context,
                BaseWeather.Type.DEFAULT
            )
    }

    fun setWeatherType(type: BaseWeather.Type?) {
        if (type == null) {
            return
        }
        isSwitchWeather = true
        preDrawer =BaseWeather.makeDrawerByType(context,type)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h

        preDrawer!!.setSize(mWidth,mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(isSwitchWeather){
            isSwitchWeather=false
            preDrawer!!.setSize(mWidth,mHeight)
        }
        preDrawer.draw(canvas,1f)
        invalidate()
    }

}

