package com.wstik.kinde.domain

import com.wstik.kinde.data.sources.AuthDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class LogoutUseCase(
    private val authDataSource: AuthDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, Unit>(schedulerBackground, schedulerMainThread) {
    override fun onCreate(parameter: Unit?): Flowable<Unit> {
        return authDataSource.logout()
            .andThen(Flowable.just(Unit))
    }
}