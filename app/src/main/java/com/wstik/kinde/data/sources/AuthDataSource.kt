package com.wstik.kinde.data.sources

import com.wstik.kinde.data.requests.SignUpRequest
import io.reactivex.Completable

interface AuthDataSource {
    fun login() : Completable
    fun signUp(request: SignUpRequest) : Completable
    fun forgotPassword() : Completable
}