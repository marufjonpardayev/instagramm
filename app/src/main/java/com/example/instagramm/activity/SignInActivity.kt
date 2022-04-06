package com.example.instagramm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramm.R

class SignInActivity : AppCompatActivity() {
    //private val tag=SignInActivity::class.java.simpleName
    private lateinit var etEmail:EditText
    private lateinit var etPassword:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews() {
        etEmail=findViewById(R.id.et_email)
        etPassword=findViewById(R.id.et_password)
        val bSignIn=findViewById<Button>(R.id.b_signin)
        bSignIn.setOnClickListener {
            callMainActivity()
        }
        val tvSignUp=findViewById<TextView>(R.id.tv_signup)
        tvSignUp.setOnClickListener {
            callSignUpActivity()
        }
    }

    private fun callMainActivity() {
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun callSignUpActivity() {

    }
}