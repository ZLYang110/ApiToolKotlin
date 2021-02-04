package com.zlyandroid.apitoolkotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zlyandroid.apitoolkotlin.db.dao.DiaryDao
import com.zlyandroid.apitoolkotlin.db.dao.HobbyDao
import com.zlyandroid.apitoolkotlin.db.dao.SkillDao
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:DB
 */


@Database(
    entities = [
        SkillModel::class,
        HobbyModel::class,
        DiaryModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ApiRoom : RoomDatabase() {
    abstract fun skillDao(): SkillDao
    abstract fun hobbyDao(): HobbyDao
    abstract fun diaryDao(): DiaryDao
}