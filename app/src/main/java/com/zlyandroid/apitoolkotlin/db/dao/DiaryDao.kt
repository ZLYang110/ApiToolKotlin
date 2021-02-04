package com.zlyandroid.apitoolkotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zlyandroid.apitoolkotlin.db.model.DiaryModel
import com.zlyandroid.apitoolkotlin.db.model.HobbyModel
import com.zlyandroid.apitoolkotlin.db.model.SkillModel

/**
 * @author zhangliyang
 * @date 2020/12/18
 * GitHub: https://github.com/ZLYang110
 * desc:签到日记 Dao
 */

@Dao
interface DiaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg mode: DiaryModel)

    @Query("DELETE FROM DiaryModel WHERE  id= :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM DiaryModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM DiaryModel WHERE  data= :data")
    suspend fun findById(data: String): DiaryModel?

    @Query("SELECT * FROM DiaryModel ORDER BY time DESC LIMIT (:offset), (:count)")
    suspend fun findAll(offset: Int, count: Int): List<DiaryModel>

    @Query("SELECT * FROM DiaryModel ORDER BY time DESC")
    suspend fun findAll(): List<DiaryModel>
}