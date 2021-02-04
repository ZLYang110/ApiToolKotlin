package com.zlyandroid.wankotlin.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
/**
 * @author zhangliyang
 * @date 2020/11/13
 * GitHub: https://github.com/ZLYang110
 */
class MultiFragmentPagerAdapter<E, F : Fragment> : FragmentStatePagerAdapter {

    private lateinit var mCreator: FragmentCreator<E, F>
    private var mDataList: List<E>? = null

    constructor(fm: FragmentManager, creator: FragmentCreator<E, F>) : super(fm){
        creator
    }

    fun getDataList(): List<E>? {
        return mDataList
    }

    fun setDataList(dataList: List<E>) {
        mDataList = dataList
        notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return if (mDataList == null) 0 else mDataList!!.size
    }

    override fun getItem(position: Int): Fragment {
        return mCreator.create(mDataList!![position], position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCreator.getTitle(mDataList!![position]);
    }
    interface FragmentCreator<E, F> {
        fun create(data: E, pos: Int): F
        fun getTitle(data: E): String?
    }
}

