package com.wstik.kinde.domain

import com.wstik.kinde.data.requests.ForgotPasswordRequest
import com.wstik.kinde.data.sources.AuthDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class ForgotPasswordUseCase(
    private val authDataSource: AuthDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread:Scheduler
) : UseCase<ForgotPasswordRequest, Unit>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: ForgotPasswordRequest?): Flowable<Unit> {
        return authDataSource.forgotPassword(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}