package com.wstik.kinde.presentation.auth.forgotpassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.FormError
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.ForgotPasswordForm
import com.wstik.kinde.data.models.FormState
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

fun Context.startForgotPassword() {
    startActivity(Intent(this, ForgotPasswordActivity::class.java))
}

class ForgotPasswordActivity : AppCompatActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        viewModel.resetPasswordState.observe(this, Observer { handleResetPasswordState(it) })
        viewModel.formState.observe(this, Observer { handleFormState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.forgot_password_title)

        buttonSubmit.setOnClickListener { viewModel.forgotPassword() }
        inputEmail.addTextChangedListener(afterTextChanged = { editable ->
            viewModel.formState.value = ForgotPasswordForm(editable.toString())
        })
        inputEmail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.forgotPassword()
            }
            false
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

    private fun handleFormState(state: FormState) {
        val isValid = state.isValid()
        buttonSubmit.isEnabled = isValid
        when (state.firstError()) {
            FormError.BAD_EMAIL -> {
                inputLayoutEmail.error = getString(R.string.error_email_not_valid)
            }
            else -> {
                inputLayoutEmail.error = null
            }
        }
    }

    private fun handleResetPasswordState(state: LoadState<Unit>?) {
        progressBar.visibility = if (state is LoadState.Loading) View.VISIBLE else View.GONE
        when (state) {
            is LoadState.Data -> Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show()
            is LoadState.Error -> {
                Toast.makeText(this, "Error sending email!", Toast.LENGTH_SHORT).show()
                Timber.d(state.throwable)
            }
        }
    }
}
