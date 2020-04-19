package com.wstik.kinde.data.requests

sealed class AuthRequest {
    data class Email(val email: String, val password: String) : AuthRequest()
    data class Google(val token: String) : AuthRequest()
    data class Facebook(val token: String) : AuthRequest()
}