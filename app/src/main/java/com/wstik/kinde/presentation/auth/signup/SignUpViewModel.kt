package com.wstik.kinde.presentation.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.requests.SignUpRequest
import com.wstik.kinde.domain.SignUpUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    val signUpState = MutableLiveData<LoadState<Unit>>()
    val formState = MutableLiveData<LoadState<Unit>>()
    private val compositeDisposable = CompositeDisposable()

    fun signUpEmail(email: String, password: String) {
        signUp(SignUpRequest.Email(email, password))
    }

    fun signUpGoogle() {
        signUp(SignUpRequest.Google)
    }

    fun signUpFacebook() {
        signUp(SignUpRequest.Facebook)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun signUp(request: SignUpRequest) {
        val disposable = signUpUseCase.execute(request)
            .doOnSubscribe { signUpState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { signUpState.value = LoadState.Data(it) },
                onError = { signUpState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}