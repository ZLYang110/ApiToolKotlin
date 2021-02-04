package com.zlyandroid.apitoolkotlin.util

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 *  desc: 日期转换
 */
object DateUtil {

    var yyyy_mm_dd = "yyyy-MM-dd"
    var yyyymmdd = "yyyyMMdd"
    var yyyymmddhhmmss = "yyyyMMddHHmmss"
    var yyyymmdd_hh_mm_ss = "yyyyMMdd_HH:mm:ss"
    var yyyy_mm_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss"

    /**
     * 获取现在时间
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    fun getNowDate(): Date {
        val simpleDateFormat = SimpleDateFormat(yyyy_mm_dd_hh_mm_ss) // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        return date
    }

    fun getNowDate(format: String): Date {
        val simpleDateFormat = SimpleDateFormat(format) // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        return date
    }

    fun getNowTime(): String {
        val simpleDateFormat = SimpleDateFormat(yyyy_mm_dd_hh_mm_ss) // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }

    fun getNowTime(format: String): String {
        val simpleDateFormat = SimpleDateFormat(format) // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }

    /**
     * 把Data日期转成String
     * @return
     */
      fun getTime(format: String,date: Date): String { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    /**
     * 将长时间格式字符串转换为时间 format
     * @param strDate
     * @return
     */
    fun strToDateLong(strDate: String, format: String): Date {
        val formatter = SimpleDateFormat(format)
        val pos = ParsePosition(0)
        val strtodate = formatter.parse(strDate, pos)
        return strtodate
    }

    /**
     *  将时间格式转换为字符串
     * @param strDate
     * @return
     */
    fun strToDateLong(dateDate: Date, format: String): String {
        val formatter = SimpleDateFormat(format)
        val dateString = formatter.format(dateDate)
        return dateString
    }


    /**
     *  根据生日获取年龄
     * @param birthDay
     * @return Age
     */
    fun BirthdayToAge(birthDay: Date): Int {
        val cal = Calendar.getInstance()
        if (cal.before(birthDay)) {
            return 0
        }
        val yearNow = cal[Calendar.YEAR]
        val monthNow = cal[Calendar.MONTH]
        val dayOfMonthNow = cal[Calendar.DAY_OF_MONTH]

        cal.time = birthDay
        val yearBirth = cal[Calendar.YEAR]
        val monthBirth = cal[Calendar.MONTH]
        val dayOfMonthBirth = cal[Calendar.DAY_OF_MONTH]

        var age = yearNow - yearBirth
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age

    }

}