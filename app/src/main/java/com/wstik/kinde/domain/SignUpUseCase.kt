package com.wstik.kinde.domain

import com.wstik.kinde.data.requests.SignUpRequest
import com.wstik.kinde.data.sources.AuthDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class SignUpUseCase(
    private val authDataSource: AuthDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<SignUpRequest, Unit>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: SignUpRequest?): Flowable<Unit> {
        return authDataSource.signUp(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}