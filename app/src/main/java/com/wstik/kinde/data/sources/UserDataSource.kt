package com.wstik.kinde.data.sources

import com.wstik.kinde.data.models.UserProfile
import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun updateName(name: String) : Completable

    fun isUserComplete() : Single<Boolean>

    fun getUserProfile() : Single<UserProfile>
}