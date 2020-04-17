package com.wstik.kinde.domain

import com.wstik.kinde.data.requests.ResetPasswordRequest
import com.wstik.kinde.data.sources.AuthDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class ResetPasswordUseCase(
    private val authDataSource: AuthDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread:Scheduler
) : UseCase<ResetPasswordRequest, Unit>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: ResetPasswordRequest?): Flowable<Unit> {
        return authDataSource.resetPassword(parameter!!)
            .andThen(Flowable.just(Unit))
    }
}