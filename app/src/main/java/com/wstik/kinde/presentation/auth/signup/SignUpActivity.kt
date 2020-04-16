package com.wstik.kinde.presentation.auth.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.wstik.kinde.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.toolbar.view.*

fun Context.startSignUp() {
    startActivity(Intent(this, SignUpActivity::class.java))
}

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.sign_up_title)

        inputEmail.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?){

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
