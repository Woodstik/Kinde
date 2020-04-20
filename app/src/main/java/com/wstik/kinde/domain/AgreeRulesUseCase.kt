package com.wstik.kinde.domain

import com.wstik.kinde.data.requests.AgreeRulesRequest
import com.wstik.kinde.data.sources.UserDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class AgreeRulesUseCase(
    private val userDataSource: UserDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<AgreeRulesRequest, Unit>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: AgreeRulesRequest?): Flowable<Unit> {
        return userDataSource.updateName(parameter!!.signature)
            .andThen(Flowable.just(Unit))
    }
}