package com.zlyandroid.apitoolkotlin.core

import com.zlyandroid.apitoolkotlin.db.executor.HobbyExecutor
import com.zlyandroid.apitoolkotlin.db.executor.SkillExecutor
import com.zlyandroid.apitoolkotlin.ui.mine.bean.UserInfoBean
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.PreUtils
import com.zlyandroid.basic.common.util.log.L

object Data {

    fun init(){
        initUserInfo()
       // initLable()
        //initIndustryLable()
    }

    fun initUserInfo(){
        UserInfoManager.getUserInfo()?.let  {

        } ?: let {
            var userInfoBean= UserInfoBean();
            userInfoBean.userId="1"
            userInfoBean.userNickname ="private"
            userInfoBean.passWord = "123456"
            userInfoBean.age = 18
            userInfoBean.birthday ="1992-08-25"
            userInfoBean.githubUrl = "https://github.com/ZLYang110"
            userInfoBean.autograph = "想的太多，做的太少，中间的落差就是烦恼，要么去做，要么别想..."
            userInfoBean.labelList.add("Android")
            userInfoBean.labelList.add("java")
            userInfoBean.labelList.add("Kotlin")
            userInfoBean.interestList.add("骑车")
            userInfoBean.interestList.add("游泳")
            userInfoBean.interestList.add("旅游")
            UserInfoManager.saveUserInfo(userInfoBean)
            L.i("sssssssssssssssssssssssssssss"+UserInfoManager.getUserInfo()?.toFormatJson())
        }


    }

    //技能标签
    fun initLable(){
       var mSkillExecutor = SkillExecutor()
        mSkillExecutor.add( object : SimpleListener {
            override fun onResult() {
               // showToast("添加成功")
            }
        }, object : SimpleListener {
            override fun onResult() {
                //showToast("添加失败")
            }
        },"Android","java","object-c","Kotlin","C语言","swift","python","php","H5","C#","Ruby","C++","Rails",".net","Scala")
    }
    //行业标签
    fun initIndustryLable(){
        var mHobbyExecutor = HobbyExecutor()
        mHobbyExecutor.add( object : SimpleListener {
            override fun onResult() {
                // showToast("添加成功")
            }
        }, object : SimpleListener {
            override fun onResult() {
                // showToast("添加失败")
            }
        },"互联网","人工智能","AR","VR","教育","服务","投资","金融")
    }

    public var overtextTip: List<String> = listOf("往日已成过往云烟~","往日已去不复返~","往事已过,韶华已尽~","人生若只如初见~","过往是覆水难收的~","日子已经过去，过去式你还不会用~")
    public var futuretextTip: List<String> = listOf("加油呀~","棒棒哒~","努力吧少年~","努力吧少年~","努力努力再努力~","努力吧少年~")

}