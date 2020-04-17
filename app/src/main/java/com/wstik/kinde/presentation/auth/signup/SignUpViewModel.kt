package com.wstik.kinde.presentation.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.SignUpForm
import com.wstik.kinde.data.requests.AuthRequest
import com.wstik.kinde.domain.SignUpUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    val signUpState = MutableLiveData<LoadState<Unit>>()
    val formState = MutableLiveData<SignUpForm>()

    private val compositeDisposable = CompositeDisposable()

    fun signUpEmail() {
        formState.value?.let {
            if(it.isValid()) {
                signUp(AuthRequest.Email(it.email, it.password))
            }
        }
    }

    fun signUpGoogle() {
        signUp(AuthRequest.Google)
    }

    fun signUpFacebook() {
        signUp(AuthRequest.Facebook)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun signUp(request: AuthRequest) {
        val disposable = signUpUseCase.execute(request)
            .doOnSubscribe { signUpState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { signUpState.value = LoadState.Data(it) },
                onError = { signUpState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}