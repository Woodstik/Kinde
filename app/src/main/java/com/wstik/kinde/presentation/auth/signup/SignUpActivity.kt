package com.wstik.kinde.presentation.auth.signup

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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.FormError
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.FormState
import com.wstik.kinde.data.models.SignUpForm
import com.wstik.kinde.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException


fun Context.startSignUp() {
    startActivity(Intent(this, SignUpActivity::class.java))
}

private const val REQUEST_GOOGLE_SIGN_IN = 1

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel.signUpState.observe(this, Observer { handleSignUpState(it) })
        viewModel.formState.observe(this, Observer { handleFormState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.sign_up_title)

        buttonSignUp.setOnClickListener {
            viewModel.signUpEmail()
            hideKeyboard(it)
        }
        buttonGoogle.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, REQUEST_GOOGLE_SIGN_IN)
        }
        buttonFacebook.setOnClickListener { viewModel.signUpFacebook() }

        inputEmail.addTextChangedListener(afterTextChanged = { editable ->
            viewModel.formState.value =
                SignUpForm(editable.toString(), inputPassword.text.toString())
        })
        inputPassword.addTextChangedListener(afterTextChanged = { editable ->
            viewModel.formState.value =
                SignUpForm(inputEmail.text.toString(), editable.toString())
        })
        inputPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.signUpEmail()
            }
            false
        }
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun handleSignUpState(state: LoadState<Unit>?) {
        progressBar.visibility = if (state is LoadState.Loading) View.VISIBLE else View.GONE
        enableForm(state !is LoadState.Loading)
        when (state) {
            is LoadState.Data -> Toast.makeText(this, "User Created!", Toast.LENGTH_SHORT).show()
            is LoadState.Error -> {
                when (state.throwable) {
                    is IOException -> ""
                    is FirebaseAuthWeakPasswordException -> ""
                    is FirebaseAuthInvalidCredentialsException -> ""
                    is FirebaseAuthUserCollisionException -> ""
                    is FirebaseAuthInvalidUserException -> ""
                    else -> ""
                }
            }
        }
    }

    private fun handleFormState(state: FormState) {
        val isValid = state.isValid()
        buttonSignUp.isEnabled = isValid
        when (state.firstError()) {
            FormError.BAD_EMAIL -> {
                inputLayoutEmail.error = getString(R.string.error_email_not_valid)
                inputLayoutPassword.error = null
            }
            FormError.SHORT_PASSWORD -> {
                inputLayoutEmail.error = null
                inputLayoutPassword.error = getString(R.string.error_password_too_short)
            }
            else -> {
                inputLayoutEmail.error = null
                inputLayoutPassword.error = null
            }
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

    private fun enableForm(enable: Boolean) {
        inputLayoutEmail.isEnabled = enable
        inputLayoutPassword.isEnabled = enable
        val form = viewModel.formState.value
        buttonSignUp.isEnabled = enable && (form == null || form.isValid())
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.signUpGoogle(account?.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, R.string.error_google_sign_in, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
