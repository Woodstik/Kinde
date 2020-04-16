package com.wstik.kinde.presentation.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R
import com.wstik.kinde.presentation.auth.login.startLogin
import com.wstik.kinde.presentation.auth.signup.startSignUp
import kotlinx.android.synthetic.main.activity_auth_main.*

fun Context.startAuthMain(){
    startActivity(Intent(this, AuthMainActivity::class.java))
}

class AuthMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_main)

        buttonSignUp.setOnClickListener { startSignUp() }
        buttonLogin.setOnClickListener { startLogin() }
    }
}
