package com.wstik.kinde.di

import com.wstik.kinde.domain.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory { SignUpUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { LoginUseCase(get(), get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { ResetPasswordUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { AgreeRulesUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetAuthStateUseCase(get(), get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { GetUserProfileUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { LogoutUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
}