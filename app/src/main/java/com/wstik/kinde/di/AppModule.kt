package com.wstik.kinde.di

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SCHEDULER_IO = "SCHEDULER_IO"
const val SCHEDULER_MAIN_THREAD = "SCHEDULER_MAIN_THREAD"
const val SCHEDULER_COMPUTATION = "SCHEDULER_COMPUTATION"

val appModule = module {
    single(named(SCHEDULER_IO)) { Schedulers.io() }
    single(named(SCHEDULER_MAIN_THREAD)) { AndroidSchedulers.mainThread() }
    single(named(SCHEDULER_COMPUTATION)) { Schedulers.computation() }
    single { FirebaseAuth.getInstance() }
}