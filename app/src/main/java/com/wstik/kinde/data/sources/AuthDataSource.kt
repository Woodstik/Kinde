package com.wstik.kinde.data.sources

import com.wstik.kinde.data.requests.AuthRequest
import io.reactivex.Completable

interface AuthDataSource {
    fun login(request: AuthRequest) : Completable
    fun signUp(request: AuthRequest) : Completable
    fun forgotPassword() : Completable
}