package com.zlyandroid.apitoolkotlin.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhangliyang
 * @date 2020/12/15
 * GitHub: https://github.com/ZLYang110
 * desc:爱好标签
 */

@Entity
data class HobbyModel (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val hobbyName: String,
    val time: Long
)