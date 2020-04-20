package com.wstik.kinde.di

import com.wstik.kinde.data.sources.remote.AuthService
import com.wstik.kinde.data.sources.remote.UserService
import org.koin.dsl.module

val remoteModule = module {
    single { AuthService(get()) }
    single { UserService(get()) }
}