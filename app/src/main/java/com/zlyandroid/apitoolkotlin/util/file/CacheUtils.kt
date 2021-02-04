package com.zlyandroid.apitoolkotlin.util.file

import com.zlyandroid.apitoolkotlin.app.AppConfig
import java.io.File

object CacheUtils {
    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    fun getCacheDir(): String  {
        lateinit var cacheFile: File
        if (FileUtils.isSDCardAlive()) {
            cacheFile = AppConfig.getContext().getExternalCacheDir()!!
        }
        if (cacheFile == null) {
            cacheFile = AppConfig.getContext().getCacheDir()
        }
        return  cacheFile.absolutePath
    }

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    fun getTotalCacheSize(): String? {
        var cacheSize = FileUtils.getSize(AppConfig.getContext().getCacheDir())
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(AppConfig.getContext().getExternalCacheDir()!!)
        }
        return FileUtils.formatSize(cacheSize.toDouble())
    }

    /**
     * 清除系统默认缓存文件夹内的缓存
     */
    fun clearAllCache() {
        FileUtils.delete(AppConfig.getContext().getCacheDir())
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(AppConfig.getContext().getExternalCacheDir())
        }
    }
}