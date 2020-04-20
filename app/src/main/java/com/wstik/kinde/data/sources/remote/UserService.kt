package com.wstik.kinde.data.sources.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.wstik.kinde.utils.toCompletable
import io.reactivex.Completable

class UserService(private val firebaseAuth: FirebaseAuth) {

    fun updateName(name: String) : Completable {
        val profileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .build()
        return firebaseAuth.currentUser!!.updateProfile(profileChangeRequest).toCompletable()
    }
}