package com.gohar_amin.whishly.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.gohar_amin.whishly.R
import com.gohar_amin.whishly.utils.Utils

class RegistrationActivity : AppCompatActivity() {
    private var imageUrl: Uri?=null
    private var imageName: String?=null
    lateinit var etEmail: EditText
    lateinit var etConfirm: EditText
    lateinit var etPassword: EditText
    lateinit var btnRegister: Button
    lateinit var tvMember: TextView
    lateinit var ivProfile: ImageView
    lateinit var ivEdit: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        ivProfile=findViewById(R.id.ivProfile)
        ivEdit=findViewById(R.id.ivEdit)
        etEmail=findViewById(R.id.etEmail)
        etConfirm=findViewById(R.id.etConfirm)
        etPassword=findViewById(R.id.etPassword)
        btnRegister=findViewById(R.id.btnRegister)
        tvMember=findViewById(R.id.tvMember)
        tvMember.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
        }
        btnRegister.setOnClickListener {
            if(checkInput())
            startActivity(Intent(this@RegistrationActivity,HomeActivity::class.java))
        }
        ivEdit.setOnClickListener {
            Log.e("click","click")
            Utils.showSelectedImages(this@RegistrationActivity)
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
        else if(TextUtils.isEmpty(etConfirm.text)){
            Toast.makeText(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show()
            return false
        }
        else if(!etPassword.text.toString().equals(etConfirm.text.toString())){
            Toast.makeText(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show()
            return false
        }else if(imageName==null || imageUrl==null){
            Toast.makeText(this, "Please Select the Image", Toast.LENGTH_SHORT).show()
            return false
        }
        else{
            return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==201 && resultCode== RESULT_OK && data!=null){
            imageUrl=data.data
            imageName=imageUrl!!.lastPathSegment
            Utils.loadImage(this@RegistrationActivity,imageUrl.toString(),ivProfile)
        }
    }
}