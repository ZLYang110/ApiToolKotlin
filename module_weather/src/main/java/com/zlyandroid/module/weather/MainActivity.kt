package com.zlyandroid.module.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tb_main)
        ctl_main.setTitle("水果名称")
        val s = generateFruitContent()
        tv_fruit.setText(s)

        sunDayView.setTime("22:00","9:00","9:00","亏凸月")
    }

    private fun generateFruitContent(): String? {
        val sb = StringBuilder()
        for (i in 0..499) {
            sb.append("水果名称")
        }
        return sb.toString()
    }
}