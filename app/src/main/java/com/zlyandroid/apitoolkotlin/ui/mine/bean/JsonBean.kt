package com.zlyandroid.apitoolkotlin.ui.mine.bean

import com.contrarywind.interfaces.IPickerViewData

/**
 * @author zhangliyang
 * @date 2020/11/26
 * GitHub: https://github.com/ZLYang110
 * desc:地区选择器
 */
class JsonBean : IPickerViewData {
    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */
      var name: String = ""
      lateinit var  city: List<CityBean>

    override fun getPickerViewText(): String {
        return name
    }

    class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */
        var name: String = ""
        lateinit var area: List<String>
    }
}