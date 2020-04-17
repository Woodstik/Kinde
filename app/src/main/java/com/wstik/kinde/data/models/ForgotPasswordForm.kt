package com.wstik.kinde.data.models

import android.util.Patterns
import com.wstik.kinde.data.enums.FormError

data class ForgotPasswordForm(
    val email: String
) : FormState {

    override fun isValid(): Boolean {
        return isEmailValid()
    }

    override fun firstError(): FormError? {
        return if(email.isNotEmpty() && !isEmailValid()){
            FormError.BAD_EMAIL
        } else{
            null
        }
    }

    private fun isEmailValid() : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}