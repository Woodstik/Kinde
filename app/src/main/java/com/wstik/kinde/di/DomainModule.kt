package com.wstik.kinde.di

import com.wstik.kinde.domain.AgreeRulesUseCase
import com.wstik.kinde.domain.ResetPasswordUseCase
import com.wstik.kinde.domain.LoginUseCase
import com.wstik.kinde.domain.SignUpUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    factory { SignUpUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { LoginUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { ResetPasswordUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
    factory { AgreeRulesUseCase(get(), get(named(SCHEDULER_IO)), get(named(SCHEDULER_MAIN_THREAD))) }
}