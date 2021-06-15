package com.zlyandroid.module.weather.util

import android.content.Context
import android.util.Base64
import java.io.*

object JsonUtils  {

    fun getJsonDataFromRaw(context: Context, fileName: Int): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


}