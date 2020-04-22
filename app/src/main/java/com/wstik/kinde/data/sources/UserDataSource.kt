package com.wstik.kinde.data.sources

import io.reactivex.Completable
import io.reactivex.Single

interface UserDataSource {

    fun updateName(name: String) : Completable

    fun isUserComplete() : Single<Boolean>
}