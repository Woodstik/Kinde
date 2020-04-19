package com.wstik.kinde.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.LoginForm
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.domain.LoginUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel(){

    val loginState = MutableLiveData<LoadState<Unit>>()
    val formState = MutableLiveData<LoginForm>()

    private val compositeDisposable = CompositeDisposable()

    fun loginEmail() {
        formState.value?.let {
            if(it.isValid()) {
                login(AuthRequest.Email(it.email, it.password))
            }
        }
    }

    fun loginGoogle(token: String) {
        login(AuthRequest.Google(token))
    }

    fun loginFacebook() {
        login(AuthRequest.Facebook)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun login(request: AuthRequest) {
        val disposable = loginUseCase.execute(request)
            .doOnSubscribe { loginState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { loginState.value = LoadState.Data(it) },
                onError = { loginState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}