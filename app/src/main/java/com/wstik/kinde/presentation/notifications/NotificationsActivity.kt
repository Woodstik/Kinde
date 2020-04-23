package com.wstik.kinde.presentation.notifications

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R

fun Context.startNotifications(){
    startActivity(Intent(this, NotificationsActivity::class.java))
}

class NotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
    }
}
