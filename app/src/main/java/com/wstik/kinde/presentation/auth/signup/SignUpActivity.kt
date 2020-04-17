package com.wstik.kinde.presentation.auth.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

fun Context.startSignUp() {
    startActivity(Intent(this, SignUpActivity::class.java))
}

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel.signUpState.observe(this, Observer { handleSignUpState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.sign_up_title)

        buttonSignUp.setOnClickListener {
            viewModel.signUpEmail(inputEmail.text.toString(), inputPassword.text.toString())
            hideKeyboard(it)
        }
        buttonGoogle.setOnClickListener { viewModel.signUpGoogle() }
        buttonFacebook.setOnClickListener { viewModel.signUpFacebook() }
    }

    private fun handleSignUpState(sate: LoadState<Unit>?) {
        when(sate){
            is LoadState.Data -> Toast.makeText(this, "User Created!", Toast.LENGTH_SHORT).show()
            is LoadState.Error -> Toast.makeText(this, "Sign Up Error!", Toast.LENGTH_SHORT).show()
        }
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
