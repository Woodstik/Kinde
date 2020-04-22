package com.wstik.kinde.data.sources.repositories

import com.wstik.kinde.data.models.UserProfile
import com.wstik.kinde.data.sources.UserDataSource
import com.wstik.kinde.data.sources.remote.UserService
import io.reactivex.Completable
import io.reactivex.Single

class UserRepository(private val userService: UserService) : UserDataSource {

    override fun updateName(name: String): Completable {
        return userService.updateName(name)
    }

    override fun isUserComplete(): Single<Boolean> {
        return userService.isUseComplete()
    }

    override fun getUserProfile(): Single<UserProfile> {
        return userService.getUser()
            .map {
                UserProfile(it.email!!, it.displayName!!)
            }
    }
}