package com.zlyandroid.apitoolkotlin.db.executor

import com.zlyandroid.apitoolkotlin.db.ApiDb.db
import com.zlyandroid.apitoolkotlin.db.model.SkillModel
import com.zlyandroid.basic.common.listener.SimpleCallback
import com.zlyandroid.basic.common.listener.SimpleListener
import com.zlyandroid.basic.common.util.log.L

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:
 */

class SkillExecutor : DbExecutor() {

    fun add( success: SimpleListener, error: SimpleListener,vararg skillName: String) {
        execute({
            skillName.forEach {
                val model = SkillModel( 0, it, System.currentTimeMillis())
                val SkillModelList=  db().skillDao().findById(it)
                if(SkillModelList.isEmpty()){
                    db().skillDao().insert(model)
                }
            }

            L.i(db().skillDao().findAll())
        }, {
            success.onResult()
        }, {
            it?.message?.let {  L.i(it) }

            error.onResult()
        })
    }

    fun remove(skillName: String, success: SimpleListener, error: SimpleListener) {
        execute({
            db().skillDao().delete(skillName)
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun removeAll(success: SimpleListener, error: SimpleListener) {
        execute({
            db().skillDao().deleteAll()
        }, {
            success.onResult()
        }, {
            error.onResult()
        })
    }

    fun getList(from: Int, count: Int, success: SimpleCallback<List<SkillModel>>, error: SimpleListener) {
        execute({
            db().skillDao().findAll(from, count)
        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }

    fun getList(success: SimpleCallback<List<SkillModel>>, error: SimpleListener) {
        execute({
            db().skillDao().findAll()

        }, {
            success.onResult(it)
        }, {
            error.onResult()
        })
    }
}