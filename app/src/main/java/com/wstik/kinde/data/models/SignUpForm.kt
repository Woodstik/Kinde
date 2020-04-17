package com.wstik.kinde.data.models

import android.util.Patterns
import com.wstik.kinde.data.enums.FormError

data class SignUpForm(
    val email: String = "",
    val password: String = ""
) : FormState {

    override fun isValid() = isEmailValid() && isPasswordValid()

    override fun firstError(): FormError? {
        return if (email.isNotEmpty() && !isEmailValid()) {
            FormError.BAD_EMAIL
        } else if (password.isNotEmpty() && !isPasswordValid()) {
            FormError.SHORT_PASSWORD
        } else {
            null
        }
    }

    private fun isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid() = password.length >= 6
}