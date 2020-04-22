package com.wstik.kinde.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.AuthState
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.domain.GetAuthStateUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class SplashViewModel(
    private val getAuthStateUseCase: GetAuthStateUseCase
) : ViewModel() {

    val nextScreenState = MutableLiveData<LoadState<AuthState>>()

    private val compositeDisposable = CompositeDisposable()

    fun checkAuthState(){
        val disposable = getAuthStateUseCase.execute()
            .subscribeBy(
                onNext = { nextScreenState.value = LoadState.Data(it)},
                onError = { nextScreenState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}