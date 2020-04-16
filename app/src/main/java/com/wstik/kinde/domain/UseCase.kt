package com.wstik.kinde.domain

import io.reactivex.Flowable
import io.reactivex.Scheduler

abstract class UseCase<PARAM, RESULT>(
    private val backgroundScheduler: Scheduler,
    private val foregroundScheduler: Scheduler
) {

    protected abstract fun onCreate(parameter: PARAM?) : Flowable<RESULT>

    fun execute(parameter: PARAM? = null) : Flowable<RESULT>{
        return onCreate(parameter)
            .subscribeOn(backgroundScheduler)
            .observeOn(foregroundScheduler)
    }
}
