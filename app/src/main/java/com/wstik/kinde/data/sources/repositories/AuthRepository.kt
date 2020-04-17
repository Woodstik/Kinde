package com.wstik.kinde.data.sources.repositories

import com.wstik.kinde.data.requests.SignUpRequest
import com.wstik.kinde.data.sources.AuthDataSource
import com.wstik.kinde.data.sources.remote.AuthService
import io.reactivex.Completable

class AuthRepository(private val authService: AuthService) : AuthDataSource {

    override fun login(): Completable {
        TODO("Not yet implemented")
    }

    override fun signUp(request: SignUpRequest): Completable {
        return authService.signUp(request)
    }

    override fun forgotPassword(): Completable {
        TODO("Not yet implemented")
    }
}