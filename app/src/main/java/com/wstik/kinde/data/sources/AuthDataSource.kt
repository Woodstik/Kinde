package com.wstik.kinde.data.sources

import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ForgotPasswordRequest
import io.reactivex.Completable

interface AuthDataSource {
    fun login(request: AuthRequest) : Completable
    fun signUp(request: AuthRequest) : Completable
    fun forgotPassword(request: ForgotPasswordRequest) : Completable
}