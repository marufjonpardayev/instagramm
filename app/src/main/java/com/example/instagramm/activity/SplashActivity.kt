package com.example.instagramm.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.instagramm.R
import com.example.instagramm.databinding.ActivitySplashBinding
import com.example.instagramm.manager.AuthManager

/**
 * In Splash
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java.simpleName
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(this@SplashActivity)
                } else {
                    callSignInActivity(this@SplashActivity)
                }
            }
        }.start()
    }
}