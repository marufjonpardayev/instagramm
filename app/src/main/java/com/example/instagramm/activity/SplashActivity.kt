package com.example.instagramm.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagramm.R

/**
 * In Splash
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    val TAG=SplashActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        object :CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                callSignInActivity()
            }
        }.start()
    }

    private fun callSignInActivity() {
        val intent=Intent(this,SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}