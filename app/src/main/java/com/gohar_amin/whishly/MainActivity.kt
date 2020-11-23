package com.gohar_amin.whishly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.gohar_amin.whishly.activities.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed(object:Runnable{
            override fun run() {
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finish()
            }
        },1000)
    }
}