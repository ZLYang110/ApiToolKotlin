package com.zlyandroid.apitoolkotlin.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zlyandroid.apitoolkotlin.db.model.SkillModel

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:技能标签 Dao
 */

@Dao
interface SkillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert( mode: SkillModel)

    @Query("DELETE FROM SkillModel WHERE  skillName= :skillName")
    suspend fun delete(skillName: String)

    @Query("DELETE FROM SkillModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM SkillModel WHERE  skillName= :skillName")
    suspend fun findById(skillName: String): List<SkillModel>

    @Query("SELECT * FROM SkillModel ORDER BY time DESC LIMIT (:offset), (:count)")
    suspend fun findAll(offset: Int, count: Int): List<SkillModel>

    @Query("SELECT * FROM SkillModel ORDER BY time DESC")
    suspend fun findAll(): List<SkillModel>
}