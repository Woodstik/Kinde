package com.wstik.kinde.presentation.rules

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.FormError
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.FormState
import com.wstik.kinde.data.models.RulesForm
import com.wstik.kinde.utils.showErrorDialog
import kotlinx.android.synthetic.main.activity_rules.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

fun Context.startRules() {
    val intent = Intent(this, RulesActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

class RulesActivity : AppCompatActivity() {

    private val viewModel: RulesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        viewModel.formState.observe(this, Observer { handleFormState(it) })
        viewModel.agreeState.observe(this, Observer { handleLoadState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        setTitle(R.string.rules_title)

        buttonAgree.setOnClickListener { viewModel.agreeRules() }

        inputSignature.addTextChangedListener(afterTextChanged = { editable ->
            viewModel.formState.value = RulesForm(editable.toString())
        })
    }

    private fun handleLoadState(state: LoadState<Unit>) {
        progressBar.visibility = if (state is LoadState.Loading) View.VISIBLE else View.GONE
        enableForm(state !is LoadState.Loading)
        when (state) {
            is LoadState.Data -> Toast.makeText(this, "Name updated", Toast.LENGTH_SHORT).show()
            is LoadState.Error -> {
                when (state.throwable) {
                    is IOException, is FirebaseNetworkException -> showErrorDialog(getString(R.string.error_network))
                    is FirebaseAuthInvalidUserException -> showErrorDialog(getString(R.string.error_bad_user))
                    else -> showErrorDialog(getString(R.string.error_generic))
                }
            }
        }
    }

    private fun handleFormState(form: FormState) {
        val isValid = form.isValid()
        buttonAgree.isEnabled = isValid
        when (form.firstError()) {
            FormError.SIGNATURE_REQUIRED -> {
                inputLayoutSignature.error = getString(R.string.error_signature_required)
            }
            FormError.SIGNATURE_LENGTH -> {
                inputLayoutSignature.error =
                    getString(R.string.error_signature_length, RulesForm.SIGNATURE_MAX_LENGTH)
            }
            else -> {
                inputLayoutSignature.error = null
            }
        }
    }

    private fun enableForm(enable: Boolean) {
        inputLayoutSignature.isEnabled = enable
        val form = viewModel.formState.value
        buttonAgree.isEnabled = enable && form != null && form.isValid()
    }
}
