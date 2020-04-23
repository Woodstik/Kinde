package com.wstik.kinde.presentation.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.domain.LogoutUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class AccountViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {

    val logoutState = MutableLiveData<LoadState<Unit>>()
    private val compositeDisposable = CompositeDisposable()

    fun logout() {
        val disposable = logoutUseCase.execute()
            .doOnSubscribe { logoutState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { logoutState.value = LoadState.Data(it) },
                onError = { logoutState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}