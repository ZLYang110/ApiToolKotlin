package com.zlyandroid.apitoolkotlin.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:技能标签
 */

@Entity
data class SkillModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val skillName: String,
    val time: Long
)