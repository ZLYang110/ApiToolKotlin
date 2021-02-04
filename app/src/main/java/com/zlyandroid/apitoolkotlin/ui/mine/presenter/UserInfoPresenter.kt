package com.zlyandroid.apitoolkotlin.ui.mine.presenter

import com.zlyandroid.apitoolkotlin.db.executor.HobbyExecutor
import com.zlyandroid.apitoolkotlin.db.executor.SkillExecutor
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel
import com.zlyandroid.apitoolkotlin.ui.mine.view.UserInfoView
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.mvp.BasePresenter
import com.zlyandroid.basic.common.util.log.L

/**
 * @author zhangliyang
 * @date 2020/11/14
 * GitHub: https://github.com/ZLYang110
 */
class UserInfoPresenter : BasePresenter<UserInfoView>() {

    //技能标签
    private var mSkillExecutor: SkillExecutor? = null
    var allLabelDatas: MutableList<String> =  ArrayList()

    //行业标签
    private var mHobbyExecutor: HobbyExecutor? = null
    var allIndustryDatas: MutableList<String> =  ArrayList()

    override fun attachView(view: UserInfoView) {
        super.attachView(view)

        mSkillExecutor = SkillExecutor()
        mHobbyExecutor = HobbyExecutor()

    }

    override fun detachView() {
        super.detachView()
        mSkillExecutor?.destroy()
        mHobbyExecutor?.destroy()
    }

    fun getSkillList(){
        mSkillExecutor?. getList(success = object : SimpleCallback<List<SkillModel>> {
            override fun onResult(data: List<SkillModel>) {
                allLabelDatas   =ArrayList()
                data.forEach{
                    allLabelDatas.add(it.skillName)
                }
                L.i(allLabelDatas)
                if(allLabelDatas.size==0){
                    initSkill()
                }
                mView?.skillSuccess(allLabelDatas)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                mView?.skillFailed()
            }
        })
    }

    fun getHobbyList(){
        mHobbyExecutor?. getList(success = object : SimpleCallback<List<HobbyModel>> {
            override fun onResult(data: List<HobbyModel>) {
                allIndustryDatas=ArrayList()
                data.forEach{
                    allIndustryDatas.add(it.hobbyName)
                }
                if(allIndustryDatas.size==0){
                    initHobbyList()
                }
                mView?.hobbySuccess(allIndustryDatas)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                mView?.hobbyFailed()
            }
        })
    }


   //default addData

    fun initSkill(){
        mSkillExecutor?.add( object : SimpleListener {
            override fun onResult() {
                // showToast("添加成功")
                getSkillList()
            }
        }, object : SimpleListener {
            override fun onResult() {
                //showToast("添加失败")
            }
        },"Android","java","object-c","Kotlin","C语言","swift","python","php","H5","C#","Ruby","C++","Rails",".net","Scala")
    }

    fun initHobbyList(){
        mHobbyExecutor?.add( object : SimpleListener {
            override fun onResult() {
                // showToast("添加成功")
                getHobbyList()
            }
        }, object : SimpleListener {
            override fun onResult() {
                // showToast("添加失败")
            }
        },"互联网","人工智能","AR","VR","教育","服务","投资","金融")
    }
}