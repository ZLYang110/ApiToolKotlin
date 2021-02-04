package com.zlyandroid.apitoolkotlin.db.executor

import com.zlyandroid.apitoolkotlin.db.ApiDb.db
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.log.L

/**
 * @author zhangliyang
 * @date 2020/12/18
 * GitHub: https://github.com/ZLYang110
 * desc:
 */

class DiaryExecutor : DbExecutor() {

    fun add(  year: Int, month: Int, day: Int,tag: String,content: String, success: SimpleListener, error: SimpleListener ) {
        execute({
            val model = DiaryModel( 0, "$year-$month-$day", year, month, day,tag,content, System.currentTimeMillis())
            val modelbyId=  db().diaryDao().findById("$year-$month-$day")
            modelbyId?.let {
                db().diaryDao().delete(it.id)
            }
            L.i(db().diaryDao().findAll())
            db().diaryDao().insert(model)
            L.i(db().diaryDao().findAll())
        }, {
            success.onResult()
        }, {
            it?.message?.let {  L.i(it) }
            error.onResult()
        })
    }

    fun remove(id: Long, success: SimpleListener, error: SimpleListener) {
        execute({
            db().diaryDao().delete(id)
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun removeAll(success: SimpleListener, error: SimpleListener) {
        execute({
            db().diaryDao().deleteAll()
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun getList(from: Int, count: Int, success: SimpleCallback<List<DiaryModel>>, error: SimpleListener) {
        execute({
            db().diaryDao().findAll(from, count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun getList(success: SimpleCallback<List<DiaryModel>>, error: SimpleListener) {
        execute({
            db().diaryDao().findAll()
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun findById(year: Int, month: Int, day: Int, success: SimpleCallback<DiaryModel>, error: SimpleListener) {
        execute({
            db().diaryDao().findById("$year-$month-$day")
        }, {
            if (it != null) {
                success.onResult(it)
            }else{
                error.onResult()
            }
        }, {
            error.onResult()
        })
    }
}