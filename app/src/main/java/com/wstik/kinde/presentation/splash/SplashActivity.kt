package com.wstik.kinde.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R
import com.wstik.kinde.presentation.auth.startAuthMain

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startAuthMain()
        finish()
    }
}
