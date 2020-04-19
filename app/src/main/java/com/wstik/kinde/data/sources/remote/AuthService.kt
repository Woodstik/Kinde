package com.wstik.kinde.data.sources.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ResetPasswordRequest
import io.reactivex.Completable

class AuthService(private val firebaseAuth: FirebaseAuth) {

    fun resetPassword(request: ResetPasswordRequest): Completable {
        return Completable.create { emitter ->
            firebaseAuth.sendPasswordResetEmail(request.email)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    fun signUp(request: AuthRequest): Completable {
        return when (request) {
            is AuthRequest.Email -> signUpEmail(request)
            is AuthRequest.Google -> authGoogle(request)
            is AuthRequest.Facebook -> authFacebook(request)
        }
    }

    fun login(request: AuthRequest): Completable {
        return when (request) {
            is AuthRequest.Email -> loginEmail(request)
            is AuthRequest.Google -> authGoogle(request)
            is AuthRequest.Facebook -> authFacebook(request)
        }
    }

    private fun signUpEmail(emailRequest: AuthRequest.Email): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(emailRequest.email, emailRequest.password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    private fun authGoogle(googleRequest: AuthRequest.Google): Completable {
        return authWithCredential(GoogleAuthProvider.getCredential(googleRequest.token, null))
    }

    private fun authFacebook(facebookRequest: AuthRequest.Facebook): Completable {
        return authWithCredential(FacebookAuthProvider.getCredential(facebookRequest.token))
    }

    private fun loginEmail(emailRequest: AuthRequest.Email): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(emailRequest.email, emailRequest.password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }

    private fun authWithCredential(credential: AuthCredential) : Completable{
        return Completable.create { emitter ->
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.tryOnError(it) }
        }
    }
}