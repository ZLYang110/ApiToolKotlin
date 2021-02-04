package com.zlyandroid.apitoolkotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:爱好标签 Dao
 */

@Dao
interface HobbyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg mode: HobbyModel)

    @Query("DELETE FROM HobbyModel WHERE  hobbyName= :hobbyName")
    suspend fun delete(hobbyName: String)

    @Query("DELETE FROM HobbyModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM HobbyModel WHERE  hobbyName= :hobbyName")
    suspend fun findById(hobbyName: String): List<HobbyModel>

    @Query("SELECT * FROM HobbyModel ORDER BY time DESC LIMIT (:offset), (:count)")
    suspend fun findAll(offset: Int, count: Int): List<HobbyModel>

    @Query("SELECT * FROM HobbyModel ORDER BY time DESC")
    suspend fun findAll(): List<HobbyModel>
}