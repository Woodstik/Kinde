package com.wstik.kinde.domain

import com.wstik.kinde.data.enums.AuthState
import com.wstik.kinde.data.sources.AuthDataSource
import com.wstik.kinde.data.sources.UserDataSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class GetAuthStateUseCase(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    schedulerBackground: Scheduler,
    schedulerMainThread: Scheduler
) : UseCase<Unit, AuthState>(schedulerBackground, schedulerMainThread) {

    override fun onCreate(parameter: Unit?): Flowable<AuthState> {
        return authDataSource.isUserLoggedIn()
            .flatMap { isLoggedIn ->
                return@flatMap if (isLoggedIn) {
                    userDataSource.isUserComplete()
                        .map { isComplete ->
                            return@map if (isComplete) {
                                AuthState.COMPLETE
                            } else {
                                AuthState.ACTIVE
                            }
                        }
                } else {
                    Single.just(AuthState.NONE)
                }
            }.delay(1, TimeUnit.SECONDS).toFlowable()
    }
}