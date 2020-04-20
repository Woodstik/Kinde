package com.wstik.kinde.data.models

import com.wstik.kinde.data.enums.FormError

data class RulesForm(val signature: String) : FormState {

    companion object{
        const val SIGNATURE_MAX_LENGTH = 16
    }

    override fun isValid() = isSignatureValid()

    override fun firstError(): FormError? {
        return if(signature.isEmpty()){
            FormError.SIGNATURE_REQUIRED
        } else if(!isLengthValid()){
            FormError.SIGNATURE_LENGTH
        } else {
            null
        }
    }

    private fun isSignatureValid() = signature.isNotEmpty() && isLengthValid()

    private fun isLengthValid() = signature.length <= SIGNATURE_MAX_LENGTH
}