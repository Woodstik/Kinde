package com.wstik.kinde.data.sources.remote

import com.google.firebase.auth.FirebaseAuth
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ForgotPasswordRequest
import io.reactivex.Completable

class AuthService(private val firebaseAuth: FirebaseAuth) {

    fun forgotPassword(request: ForgotPasswordRequest): Completable {
        return Completable.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(request.email)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    fun signUp(request: AuthRequest): Completable {
        return when (request) {
            is AuthRequest.Email -> signUpEmail(request)
            is AuthRequest.Google -> signUpGoogle(request)
            is AuthRequest.Facebook -> signUpFacebook(request)
        }
    }

    fun login(request: AuthRequest): Completable {
        return when (request) {
            is AuthRequest.Email -> loginEmail(request)
            is AuthRequest.Google -> loginGoogle(request)
            is AuthRequest.Facebook -> loginFacebook(request)
        }
    }

    private fun signUpEmail(emailRequest: AuthRequest.Email): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(emailRequest.email, emailRequest.password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    private fun signUpGoogle(googleRequest: AuthRequest.Google): Completable {
        return Completable.error(NotImplementedError())
    }

    private fun signUpFacebook(facebookRequest: AuthRequest.Facebook): Completable {
        return Completable.error(NotImplementedError())
    }

    private fun loginEmail(emailRequest: AuthRequest.Email): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(emailRequest.email, emailRequest.password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    private fun loginGoogle(googleRequest: AuthRequest.Google): Completable {
        return Completable.error(NotImplementedError())
    }

    private fun loginFacebook(facebookRequest: AuthRequest.Facebook): Completable {
        return Completable.error(NotImplementedError())
    }
}