package com.zlyandroid.apitoolkotlin.ui.mine.bean

import com.zlyandroid.basic.common.mvp.BaseBean
import java.util.*

/**
 * @author zhangliyang
 * @date 2020/11/25
 * GitHub: https://github.com/ZLYang110
 * desc:
 */
class UserInfoBean : BaseBean() {
    var userId: String = "" //账号
    var userNickname: String = "" //昵称
    var userName: String = "" // 姓名
    var passWord: String = "" //密码
    var headImg:  String = "" //头像
    var sex:  Int = 0 //0男 1女
    var age:  Int = 0 //年龄
    var birthday: String = "" //生日
    var level:  Int = 0  //等级
    var region: String = ""   //地区
    var autograph: String = ""   //简介
    var githubUrl: String = ""   //github地址

    var labelList:  MutableList<String>  = ArrayList()//标签

    var interestList:  MutableList<String> =  ArrayList()//兴趣

    var position: String = ""   //职位
    var industry: String = ""   //行业
    var company: String = ""   //公司

}