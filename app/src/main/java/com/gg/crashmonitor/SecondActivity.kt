package com.gg.crashmonitor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * @description:
 * @author: GG
 * @createDate: 2023 2.16 0016 20:12
 * @updateUser:
 * @updateDate: 2023 2.16 0016 20:12
 */
class SecondActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        findViewById<Button>(R.id.button).setOnClickListener {
//            Integer.parseInt("0x01")
            var a = 1 / 0
        }

    }
}