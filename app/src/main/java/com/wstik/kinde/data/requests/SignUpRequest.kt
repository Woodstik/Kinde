package com.wstik.kinde.data.requests

sealed class SignUpRequest {
    data class Email(val email: String, val password: String) : SignUpRequest()
    object Google : SignUpRequest()
    object Facebook : SignUpRequest()
}