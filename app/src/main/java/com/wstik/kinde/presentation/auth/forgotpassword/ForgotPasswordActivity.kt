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
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.FormError
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.ForgotPasswordForm
import com.wstik.kinde.data.models.FormState
import com.wstik.kinde.utils.showErrorDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

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
        enableForm(state !is LoadState.Loading && viewModel.formState.value?.isValid()!!)
        when (state) {
            is LoadState.Data -> Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show()
            is LoadState.Error -> {
                when (state.throwable) {
                    is IOException, is FirebaseNetworkException -> showErrorDialog(getString(R.string.error_network))
                    is FirebaseAuthInvalidUserException -> showErrorDialog(getString(R.string.error_bad_user))
                    else -> showErrorDialog(getString(R.string.error_generic))
                }
            }
        }
    }

    private fun enableForm(enable: Boolean) {
        inputLayoutEmail.isEnabled = enable
        buttonSubmit.isEnabled = enable
    }
}
