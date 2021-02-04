package com.zlyandroid.apitoolkotlin.ui.mine.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.haibin.calendarview.CalendarView.OnCalendarSelectListener
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.Data
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.ui.mine.presenter.SignInPresenter
import com.zlyandroid.apitoolkotlin.ui.mine.view.SignInView
import com.zlyandroid.basic.common.base.BaseMvpActivity
import com.zlyandroid.basic.common.util.log.L
import kotlinx.android.synthetic.main.activity_signin.*
import java.util.*

/**
 * @author zhangliyang
 * @date 2020/11/17
 * GitHub: https://github.com/ZLYang110
 * desc:签到
 */
class SignInActivity : BaseMvpActivity<SignInView, SignInPresenter>(), SignInView ,OnCalendarSelectListener, CalendarView.OnYearChangeListener {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var mYear = 0

    private var nowadaySignBool: Boolean  =false //今天是否签到过
    private var nowadayCalendar: Calendar? =null //今天日期
    /*
    *  < 0  过去的日期
    *  = 0  今天
    *  > 0  将来的日期
    * */
    private var differ:Int = 0 // 选中日期和今天相差的天数

    override fun createPresenter(): SignInPresenter = SignInPresenter()

    override fun getLayoutID(): Int = R.layout.activity_signin

    override fun initView() {
        nowadayCalendar = calendarView.selectedCalendar
        mYear = calendarView.getCurYear()
        abc.titleTextView.setText(
            "" + calendarView.curYear + "年" +
                    calendarView.getCurMonth().toString() + "月" + calendarView.getCurDay() + "日"
        )
    }

    override fun initData() {
        abc.titleTextView.setOnClickListener {
            if (!calendarLayout.isExpand()) {
                calendarLayout.expand()
                return@setOnClickListener
            }
            calendarView.showYearSelectLayout(mYear)
            abc.titleTextView.setText(mYear.toString())
        }
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnYearChangeListener(this)
        mPresenter?.getList()
        btn_add.setOnClickListener(this)
        btn_sign.setOnClickListener(this)
    }


    override fun onClick2(v: View) {
        super.onClick2(v)
        when (v.id) {

            R.id.btn_sign -> {
                if(nowadaySignBool){
                    showToast("今天已签到")
                }else{
                    mPresenter?.toDaySign(nowadayCalendar!!.year, nowadayCalendar!!.month, nowadayCalendar!!.day )
                }
            }
            R.id.btn_add -> {
                EditRecActivity.start( getActivity(), nowadayCalendar!!.year, nowadayCalendar!!.month, nowadayCalendar!!.day )
            }
        }
    }

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text

        // calendar.addScheme(Scheme())
       // calendar.addScheme(-0xff7800, "假")
       // calendar.addScheme(-0xff7800, "节")
        return calendar
    }
    override fun onCalendarOutOfRange(calendar: Calendar) {

    }

    @SuppressLint("RestrictedApi")
    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        mYear = calendar.year
        abc.titleTextView.setText("" + calendar.year + "年" + calendar.month.toString() + "月" + calendar.day + "日")
        differ = calendar.differ(nowadayCalendar)
        if(calendar.isCurrentDay){
            btn_add.visibility = View.VISIBLE
            ll_bottom_sign.visibility = View.VISIBLE
        }else{
            btn_add.visibility = View.GONE
            ll_bottom_sign.visibility = View.GONE
        }
        mPresenter?.findById(calendar.year, calendar.month, calendar.day)
        L.i(
            "  -- " + calendar.year +
                    "  --  " + calendar.month +
                    "  -- " + calendar.day +
                    "  --  " + isClick + "  --   " + calendar.solarTerm + "  --   " + calendar.solarTerm + "  --   " + calendar.isCurrentDay + "  --   " + calendar.differ(
                nowadayCalendar
            )
        )
    }

    override fun onYearChange(year: Int) {
       // mTextMonthDay.setText(year.toString())
        abc.titleTextView.setText(year.toString())
    }

    /**
    * 签到返回
    * */
    override fun toDaySignSuccess() {
        btn_sign.setBackgroundResource(R.drawable.btn_gray_radius_max)
        nowadaySignBool=true
        mPresenter?.getList()
        showToast("签到成功")
    }

    override fun toDaySignFailed() {
        showToast("签到失败")
    }

    /**
     * 全部日记
     * */
    override fun diaryListSuccess(data: List<DiaryModel>) {
        val map: MutableMap<String, Calendar> = HashMap()
        data.forEach {
            map[getSchemeCalendar(it.year, it.month, it.day, -0x123a93, it.tag).toString()] =
                getSchemeCalendar(it.year, it.month, it.day, -0x123a93, it.tag)
            if((it.year.toString()+ it.month.toString()+it.day).equals( nowadayCalendar!!.year.toString()+ nowadayCalendar!!.month.toString()+nowadayCalendar!!.day)){
                btn_sign.setBackgroundResource(R.drawable.btn_gray_radius_max)
                nowadaySignBool=true
            }
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendarView.setSchemeDate(map)
        tv_dayNum.setText(data.size.toString())
        calendarView.scrollToCurrent()
    }

    override fun diaryListFailed() {

    }

    /**
     * 查询指定日期日记
     * */
    override fun diarySuccess(data: DiaryModel) {
        ll_bottom_content.visibility = View.VISIBLE
        content.setText(data.content)
    }

    override fun diaryFailed() {
        if(differ<0){
            ll_bottom_content.visibility = View.VISIBLE
            val random = Random()
            content.setText(Data.overtextTip.get(random.nextInt(5)))
            content.gravity = Gravity.CENTER_HORIZONTAL
        }else if(differ==0){
            ll_bottom_content.visibility = View.GONE
        }else if(differ>0){
            ll_bottom_content.visibility = View.VISIBLE
            val random = Random()
            content.setText(Data.futuretextTip.get(random.nextInt(5)))
            content.gravity = Gravity.CENTER_HORIZONTAL
        }

    }

    override fun removeSuccess() {
        TODO("Not yet implemented")
    }

    override fun removeFailed() {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === 1) {
            mPresenter?.getList()
        }
    }

}