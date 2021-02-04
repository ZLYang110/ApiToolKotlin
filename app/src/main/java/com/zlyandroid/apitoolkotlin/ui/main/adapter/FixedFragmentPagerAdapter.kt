package com.zlyandroid.wankotlin.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author zhangliyang
 * @date 2020/11/13
 * GitHub: https://github.com/ZLYang110
 */
@Suppress("DEPRECATION")
class FixedFragmentPagerAdapter : FragmentPagerAdapter {

    private lateinit var mFragments: Array<Fragment>
    private lateinit var mTitles: Array<String>

    constructor(fm: FragmentManager) : super(fm)


    open fun setFragmentList(vararg fragments: Fragment) {

        fragments.forEach {  mFragments.plus(it) }
        notifyDataSetChanged()
    }

    open fun setTitles(vararg titles: String) {
        titles.forEach {  mTitles.plus(it) }
    }


    override fun getCount(): Int = if (mFragments == null) 0 else mFragments.size

    override fun getItem(position: Int): Fragment = mFragments[position]


    override fun getPageTitle(position: Int): CharSequence? {
        if (mTitles != null && mTitles.size == count) {
            return mTitles[position]
        }
        val fragment = mFragments[position]
        if (fragment is PageTitle) {
            val pageTitle = fragment as PageTitle
            return pageTitle.pageTitle
        }
        return ""
    }

    interface PageTitle {
        val pageTitle: CharSequence
    }
}

