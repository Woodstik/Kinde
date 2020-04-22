package com.wstik.kinde.domain

import com.wstik.kinde.data.models.UserProfile
import com.wstik.kinde.data.sources.UserDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class GetUserProfileUseCase(
    private val userDataSource: UserDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, UserProfile>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: Unit?): Flowable<UserProfile> {
        return userDataSource.getUserProfile()
            .toFlowable()
    }
}