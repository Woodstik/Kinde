package com.wstik.kinde.data.sources.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.requests.ResetPasswordRequest
import com.wstik.kinde.utils.toCompletable
import io.reactivex.Completable
import io.reactivex.Single

class AuthService(private val firebaseAuth: FirebaseAuth) {

    fun logout(): Completable {
        return Completable.fromAction { firebaseAuth.signOut() }
    }

    fun isUserLoggedIn(): Single<Boolean> {
        return Single.just(firebaseAuth.currentUser != null)
    }

    fun resetPassword(request: ResetPasswordRequest): Completable {
        return firebaseAuth.sendPasswordResetEmail(request.email).toCompletable()
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
        return firebaseAuth.createUserWithEmailAndPassword(
            emailRequest.email,
            emailRequest.password
        ).toCompletable()
    }

    private fun authGoogle(googleRequest: AuthRequest.Google): Completable {
        return authWithCredential(GoogleAuthProvider.getCredential(googleRequest.token, null))
    }

    private fun authFacebook(facebookRequest: AuthRequest.Facebook): Completable {
        return authWithCredential(FacebookAuthProvider.getCredential(facebookRequest.token))
    }

    private fun loginEmail(emailRequest: AuthRequest.Email): Completable {
        return firebaseAuth.signInWithEmailAndPassword(emailRequest.email, emailRequest.password).toCompletable()
    }

    private fun authWithCredential(credential: AuthCredential): Completable {
        return firebaseAuth.signInWithCredential(credential).toCompletable()
    }
}