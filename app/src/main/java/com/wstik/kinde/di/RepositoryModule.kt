package com.wstik.kinde.di

import com.wstik.kinde.data.sources.AuthDataSource
import com.wstik.kinde.data.sources.UserDataSource
import com.wstik.kinde.data.sources.repositories.AuthRepository
import com.wstik.kinde.data.sources.repositories.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthDataSource> { AuthRepository(get()) }
    single<UserDataSource> { UserRepository(get()) }
}