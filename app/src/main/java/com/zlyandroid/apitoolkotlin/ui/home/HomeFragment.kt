package com.zlyandroid.apitoolkotlin.ui.home

import android.view.View
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.db.executor.DiaryExecutor
import com.zlyandroid.apitoolkotlin.db.executor.SkillExecutor
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel
import com.zlyandroid.apitoolkotlin.ui.home.presenter.HomePresenter
import com.zlyandroid.apitoolkotlin.ui.home.view.HomeView
import com.zlyandroid.apitoolkotlin.ui.mine.activity.UserInfoActivity
import com.zlyandroid.basic.common.base.BaseMvpFragment
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.log.L
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * @author zhangliyang
 * @date 2020/11/13
 * GitHub: https://github.com/ZLYang110
 * desc:首页
 */
class HomeFragment() : BaseMvpFragment<HomeView, HomePresenter>(), HomeView  {

    private var mSkillExecutor: SkillExecutor? = null
    private var mDiaryExecutor: DiaryExecutor? = null

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun createPresenter(): HomePresenter = HomePresenter()

    override fun getLayoutID(): Int = R.layout.fragment_home

    override fun initView() {
        mSkillExecutor = SkillExecutor()
        mDiaryExecutor = DiaryExecutor()

        btn_login.setOnClickListener(this)
        btn_insert.setOnClickListener(this)
        btn_delect.setOnClickListener(this)
        btn_select.setOnClickListener(this)
    }

    override fun onClick2(v: View) {
        when(v.id){
            R.id.btn_login -> {
                val username = et_username.text.toString()
                val password = et_password.text.toString()
                //mPresenter?.login(username, password)
                UserInfoActivity.start(context)
            }
            R.id.btn_insert -> {
                val username = et_username.text.toString()
                mDiaryExecutor?.add(2020,12,15,"null","",object : SimpleListener {
                    override fun onResult() {
                        showToast("添加成功")
                    }
                }, object : SimpleListener {
                    override fun onResult() {
                        showToast("添加失败")
                    }
                })
                mDiaryExecutor?.add(2020,12,13,"记","只有一件事才会让人感到心累\n" +
                        "犹豫不定和没把握\n" +
                        "每做一件事都是一种解放\n" +
                        "即使坏的作为也比什么都不做要好\n"
                        ,object : SimpleListener {
                    override fun onResult() {
                        showToast("添加成功")
                    }
                }, object : SimpleListener {
                    override fun onResult() {
                        showToast("添加失败")
                    }
                })
                mDiaryExecutor?.add(2020,12,12,"记","你今天出生了~",object : SimpleListener {
                    override fun onResult() {
                          showToast("添加成功")
                    }
                }, object : SimpleListener {
                    override fun onResult() {
                         showToast("添加失败")
                    }
                })

            }
            R.id.btn_delect -> {
                val username = et_username.text.toString()
                mDiaryExecutor?.removeAll(object : SimpleListener {
                    override fun onResult() {
                        showToast("删除成功")
                    }
                }, object : SimpleListener {
                    override fun onResult() {
                        showToast("删除失败")
                    }
                })
            }
            R.id.btn_select -> {
                    mSkillExecutor?. getList(success = object : SimpleCallback<List<SkillModel>> {


                        override fun onResult(data: List<SkillModel>) {
                            L.i(data)
                        }
                    }, error = object : SimpleListener {
                        override fun onResult() {
                            showToast("添加失败")
                        }
                    })
            }
        }
    }

    override fun initData() {
    }

    override fun loginSuccess() {
       showToast("登录成功")
        UserInfoActivity.start(context)
    }

    override fun loginFailed() {
        showToast("登录成功")
    }

    override fun onDestroy() {
        super.onDestroy()
        mSkillExecutor?.destroy()
        mDiaryExecutor?.destroy()
    }

}