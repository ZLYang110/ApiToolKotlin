package com.zlyandroid.apitoolkotlin.db.executor

import com.zlyandroid.apitoolkotlin.db.ApiDb.db
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.log.L

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:
 */

class HobbyExecutor : DbExecutor() {

    fun add( success: SimpleListener, error: SimpleListener,vararg hobbyName: String) {
        execute({
            hobbyName.forEach {  val model = HobbyModel( 0, it, System.currentTimeMillis())
                val SkillModelList=  db().hobbyDao().findById(it)
                if(SkillModelList.isEmpty()){
                    db().hobbyDao().insert(model)
                } }

            L.i(db().hobbyDao().findAll())
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun remove(hobbyName: String, success: SimpleListener, error: SimpleListener) {
        execute({
            db().hobbyDao().delete(hobbyName)
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun removeAll(success: SimpleListener, error: SimpleListener) {
        execute({
            db().hobbyDao().deleteAll()
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun getList(from: Int, count: Int, success: SimpleCallback<List<HobbyModel>>, error: SimpleListener) {
        execute({
            db().hobbyDao().findAll(from, count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun getList(success: SimpleCallback<List<HobbyModel>>, error: SimpleListener) {
        execute({
            db().hobbyDao().findAll()
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }
}