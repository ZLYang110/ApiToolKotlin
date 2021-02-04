package com.zlyandroid.apitoolkotlin.ui.mine.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.google.gson.Gson
import com.zlyandroid.apitoolkotlin.ui.mine.bean.JsonBean
import com.zlyandroid.basic.common.listener.SimpleCallback
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 *  desc: 城市选择器
 */
object SelectCityDialog {


    private var options1Items: List<JsonBean> = ArrayList()
    private val options2Items: ArrayList<ArrayList<String>> = ArrayList()
    private val options3Items: ArrayList<ArrayList<ArrayList<String>>> = ArrayList()


    fun show(context: Context, date: String, callback: SimpleCallback<String>) {

        // 弹出选择器
        var pvOptions  = OptionsPickerBuilder(context,
              OnOptionsSelectListener { options1, options2, options3, v -> //返回的分别是三个级别的选中位置
                  val opt1tx = if (options1Items.size > 0) options1Items.get(options1)
                      .getPickerViewText() else ""

                  val opt2tx = if (options2Items.size > 0
                      && options2Items.get(options1).size > 0
                  ) options2Items.get(options1).get(options2) else ""

                  val opt3tx =
                      if (options2Items.size > 0 && options3Items.get(options1).size > 0 && options3Items.get(
                              options1
                          ).get(options2).size > 0
                      ) options3Items.get(options1).get(options2).get(options3) else ""

                  val tx = opt1tx + "-" + opt2tx

                  callback.onResult(tx)
              })
            .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
            //.setTitleText("城市选择")
            .setDividerColor(-0xdb5263)//设置分割线的颜色
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .isAlphaGradient(true)
            .setContentTextSize(20)
            .build<Any>()


       //pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items.toList());//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items) //三级选择器

        val mDialog: Dialog =  pvOptions.getDialog()
        if (mDialog != null) {
            val params = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
            params.leftMargin = 0
            params.rightMargin = 0
            pvOptions.getDialogContainerLayout().setLayoutParams(params)
            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim) //修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f)
            }
        }
        pvOptions.show()
    }

      fun initJsonData(context: Context) { //解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         */
        val JsonData: String = getJson(context, "province.json") //获取assets目录下的json文件数据
        val jsonBean: ArrayList<JsonBean> = parseData(JsonData) //用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean
        for (i in jsonBean.indices) { //遍历省份
            val cityList = ArrayList<String>() //该省的城市列表（第二级）
            val province_AreaList = ArrayList<ArrayList<String>>() //该省的所有地区列表（第三极）
            for (c in 0 until jsonBean[i].city.size) { //遍历该省份的所有城市
                val cityName: String = jsonBean[i].city[c].name
                cityList.add(cityName) //添加城市
                val city_AreaList = ArrayList<String>() //该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean[i].city[c].area)
                province_AreaList.add(city_AreaList) //添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList)
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList)
        }
    }

    private fun parseData(result: String): ArrayList<JsonBean>  { //Gson 解析
        val detail = ArrayList<JsonBean>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return detail
    }
    private fun getJson(context: Context, fileName: String): String  {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(
                InputStreamReader(
                    assetManager.open(fileName!!)
                )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}