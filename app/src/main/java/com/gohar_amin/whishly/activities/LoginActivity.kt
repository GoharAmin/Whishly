package com.gohar_amin.whishly.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gohar_amin.whishly.R

class LoginActivity : AppCompatActivity() {
    lateinit var etEmail:EditText
    lateinit var etPassword:EditText
    lateinit var btnLogin:Button
    lateinit var tvRegisterNow:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        tvRegisterNow=findViewById(R.id.tvRegisterNow)
        tvRegisterNow.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegistrationActivity::class.java))
        }
        btnLogin.setOnClickListener {
            if(checkInput()) {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
        }

    }
    private fun checkInput(): Boolean {
        if(TextUtils.isEmpty(etEmail.text)){
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(TextUtils.isEmpty(etPassword.text)){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            return true
        }
    }
}