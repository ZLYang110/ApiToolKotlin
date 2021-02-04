package com.zlyandroid.apitoolkotlin.ui.main

import android.content.Context
import androidx.fragment.app.FragmentTransaction
import com.zlyandroid.apitoolkotlin.R
import com.zlyandroid.apitoolkotlin.core.Constants.TAG_FRAGMENT.TYPE_CATEGORY
import com.zlyandroid.apitoolkotlin.core.Constants.TAG_FRAGMENT.TYPE_DISCOVERY
import com.zlyandroid.apitoolkotlin.core.Constants.TAG_FRAGMENT.TYPE_HOME
import com.zlyandroid.apitoolkotlin.core.Constants.TAG_FRAGMENT.TYPE_MINE
import com.zlyandroid.apitoolkotlin.ui.home.HomeFragment
import com.zlyandroid.apitoolkotlin.ui.main.fragment.DiscoveryFragment
import com.zlyandroid.apitoolkotlin.ui.main.presenter.MainPresenter
import com.zlyandroid.apitoolkotlin.ui.main.view.MainView
import com.zlyandroid.apitoolkotlin.ui.mine.MineFragment
import com.zlyandroid.apitoolkotlin.ui.project.ProjectFragment
import com.zlyandroid.basic.common.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * @author zhangliyang
 * @date 2020/11/13
 * GitHub: https://github.com/ZLYang110
 * desc:主页
 */
class MainActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView {

    private var navPosition: Int = 0 //默认选中
    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mProjectFragment: ProjectFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }



    override fun initView() {
        bottom_navigation.run {
            setOnNavigationItemSelectedListener() { item ->
                when (item.itemId) {
                    R.id.action_home -> {
                        showFragment(TYPE_HOME);
                        true
                    }
                    R.id.action_square -> {
                        showFragment(TYPE_DISCOVERY);
                        true
                    }
                    R.id.action_system -> {
                        showFragment(TYPE_CATEGORY);
                        true
                    }
                    R.id.action_project -> {
                        showFragment(TYPE_MINE);
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
        mPresenter?.getQuestionListCache(2)
    }


    override fun initData() {
        showFragment(navPosition)
    }
    fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        navPosition=index
        when(index){
            TYPE_HOME -> {
                mHomeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance().let {
                    mHomeFragment = it
                    transaction.add(R.id.fragment_group, it);
                }
            }
            TYPE_DISCOVERY -> {
                mDiscoveryFragment?.let {
                    transaction.show(it)
                } ?: DiscoveryFragment.getInstance().let {
                    mDiscoveryFragment = it
                    transaction.add(R.id.fragment_group, it);
                }
            }
            TYPE_CATEGORY -> {
                mProjectFragment?.let {
                    transaction.show(it)
                } ?: ProjectFragment.getInstance().let {
                    mProjectFragment = it
                    transaction.add(R.id.fragment_group, it);
                }
            }
            TYPE_MINE -> {
                mMineFragment?.let {
                    transaction.show(it)
                } ?: MineFragment.getInstance().let {
                    mMineFragment = it
                    transaction.add(R.id.fragment_group, it);
                }
            }
        }
        transaction.commit()
    }
    fun hideFragment(transaction: FragmentTransaction){
      //  UserInfoManager.getUserInfo()?.let { L.i(it.toFormatJson()) }
        mHomeFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    override fun onResume() {
        super.onResume()
        showFragment(navPosition )
    }
    override fun onPause() {
        super.onPause()
    }
}
