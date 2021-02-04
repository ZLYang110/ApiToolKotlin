package com.zlyandroid.apitoolkotlin.ui.mine.presenter

import com.zlyandroid.apitoolkotlin.db.executor.DiaryExecutor
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel
import com.zlyandroid.apitoolkotlin.ui.main.view.MainView
import com.zlyandroid.apitoolkotlin.ui.mine.view.SignInView
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
class SignInPresenter : BasePresenter<SignInView>() {

    //技能标签
    private var mDiaryExecutor: DiaryExecutor? = null

    override fun attachView(view: SignInView) {
        super.attachView(view)

        mDiaryExecutor = DiaryExecutor()


    }

    override fun detachView() {
        super.detachView()
        mDiaryExecutor?.destroy()
    }

    fun findById(year: Int, month: Int, day: Int){
        mDiaryExecutor?. findById(year,month,day, success = object : SimpleCallback<DiaryModel> {
            override fun onResult(data: DiaryModel ) {
                L.i(data)
                mView?.diarySuccess(data)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                mView?.diaryFailed()
            }
        })
    }
    fun removeById(id: Long){
        mDiaryExecutor?. remove(id, success = object : SimpleListener{
            override fun onResult(  ) {

                mView?.removeSuccess()
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                mView?.removeFailed()
            }
        })
    }

    fun getList(){
        mDiaryExecutor?. getList( success = object : SimpleCallback<List<DiaryModel>> {

            override fun onResult(data: List<DiaryModel>) {
                 L.i(data)
                mView?.diaryListSuccess(data)
            }
        }, error = object : SimpleListener {
            override fun onResult() {
                mView?.diaryListFailed()
            }
        })
    }
    fun toDaySign(year: Int, month: Int, day: Int){
        mDiaryExecutor?.add(year,month,day,"null","",object : SimpleListener {
            override fun onResult() {
                mView?.toDaySignSuccess()
            }
        }, object : SimpleListener {
            override fun onResult() {
                mView?.toDaySignFailed()
            }
        })
    }

}