package com.zlyandroid.module.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zlyandroid.module.weather.db.dao.CityListDao
import com.zlyandroid.module.weather.db.model.CityModel

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:DB
 */


@Database(
    entities = [
        CityModel::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class ApiRoom : RoomDatabase() {
    abstract fun CityListDao(): CityListDao
}