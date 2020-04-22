package com.wstik.kinde.data.sources.repositories

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
}