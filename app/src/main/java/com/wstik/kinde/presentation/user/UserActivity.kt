package com.wstik.kinde.presentation.user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R

fun Context.startUser() {
    startActivity(Intent(this, UserActivity::class.java))
}

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}
