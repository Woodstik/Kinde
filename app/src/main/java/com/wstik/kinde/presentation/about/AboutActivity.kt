package com.wstik.kinde.presentation.about

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R

fun Context.startAbout(){
    startActivity(Intent(this, AboutActivity::class.java))
}

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
