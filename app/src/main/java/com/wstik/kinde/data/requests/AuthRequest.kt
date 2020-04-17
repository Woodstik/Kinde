package com.wstik.kinde.data.requests

sealed class AuthRequest {
    data class Email(val email: String, val password: String) : AuthRequest()
    object Google : AuthRequest()
    object Facebook : AuthRequest()
}