package com.wstik.kinde.data.sources.repositories

import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ResetPasswordRequest
import com.wstik.kinde.data.sources.AuthDataSource
import com.wstik.kinde.data.sources.remote.AuthService
import io.reactivex.Completable
import io.reactivex.Single

class AuthRepository(private val authService: AuthService) : AuthDataSource {

    override fun login(request: AuthRequest): Completable {
        return authService.login(request)
    }

    override fun signUp(request: AuthRequest): Completable {
        return authService.signUp(request)
    }

    override fun resetPassword(request: ResetPasswordRequest): Completable {
        return authService.resetPassword(request)
    }

    override fun isUserLoggedIn(): Single<Boolean> {
        return authService.isUserLoggedIn()
    }
}