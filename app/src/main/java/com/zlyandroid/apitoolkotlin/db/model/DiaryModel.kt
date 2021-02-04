package com.zlyandroid.apitoolkotlin.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zhangliyang
 * @date 2020/12/18
 * GitHub: https://github.com/ZLYang110
 * desc:签到日记
 */

@Entity
data class DiaryModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val data: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val tag: String,
    val content: String,
    val time: Long
)