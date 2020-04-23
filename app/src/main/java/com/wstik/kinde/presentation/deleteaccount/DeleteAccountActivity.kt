package com.wstik.kinde.presentation.deleteaccount

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wstik.kinde.R

fun Context.startDeleteAccount(){
    startActivity(Intent(this, DeleteAccountActivity::class.java))
}

class DeleteAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)
    }
}
