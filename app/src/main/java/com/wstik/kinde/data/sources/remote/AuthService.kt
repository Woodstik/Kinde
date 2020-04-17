package com.wstik.kinde.data.sources.remote

import com.google.firebase.auth.FirebaseAuth
import com.wstik.kinde.data.requests.SignUpRequest
import io.reactivex.Completable

class AuthService(private val firebaseAuth: FirebaseAuth) {

    fun signUp(request: SignUpRequest) : Completable {
        return when (request) {
            is SignUpRequest.Email -> signUpEmail(request)
            is SignUpRequest.Google -> signUpGoogle(request)
            is SignUpRequest.Facebook -> signUpFacebook(request)
        }
    }

    private fun signUpEmail(emailRequest: SignUpRequest.Email) : Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(emailRequest.email, emailRequest.password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    private fun signUpGoogle(googleRequest: SignUpRequest.Google) : Completable {
        return Completable.error(NotImplementedError())
    }

    private fun signUpFacebook(facebookRequest: SignUpRequest.Facebook) : Completable {
        return Completable.error(NotImplementedError())
    }
}