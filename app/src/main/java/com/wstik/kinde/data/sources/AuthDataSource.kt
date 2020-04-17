package com.wstik.kinde.data.sources

import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ResetPasswordRequest
import io.reactivex.Completable

interface AuthDataSource {
    fun login(request: AuthRequest) : Completable
    fun signUp(request: AuthRequest) : Completable
    fun resetPassword(request: ResetPasswordRequest) : Completable
}