package com.wstik.kinde.domain

import com.wstik.kinde.data.enums.AuthState
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.data.sources.AuthDataSource
import com.wstik.kinde.data.sources.UserDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler

class LoginUseCase(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<AuthRequest, AuthState>(schedulerBackground, schedulerMainThread) {
    override fun onCreate(parameter: AuthRequest?): Flowable<AuthState> {
        return authDataSource.login(parameter!!)
            .andThen(userDataSource.isUserComplete())
            .map { isComplete ->
                return@map if (isComplete) {
                    AuthState.COMPLETE
                } else {
                    AuthState.ACTIVE
                }
            }.toFlowable()
    }
}