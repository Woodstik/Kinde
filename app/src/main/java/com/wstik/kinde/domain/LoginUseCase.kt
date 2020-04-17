package com.wstik.kinde.domain

import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.sources.AuthDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class LoginUseCase(
    private val authDataSource: AuthDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<AuthRequest, Unit>(schedulerBackground, schedulerMainThread) {
    override fun onCreate(parameter: AuthRequest?): Flowable<Unit> {
        return authDataSource.login(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}