package com.wstik.kinde.presentation.auth.forgotpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.ForgotPasswordForm
import com.wstik.kinde.data.requests.ResetPasswordRequest
import com.wstik.kinde.domain.ResetPasswordUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ForgotPasswordViewModel(private val resetPasswordUseCase: ResetPasswordUseCase) :
    ViewModel() {

    val resetPasswordState = MutableLiveData<LoadState<Unit>>()
    val formState = MutableLiveData<ForgotPasswordForm>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun forgotPassword() {
        formState.value?.let {
            if(it.isValid()){
                resetPassword(ResetPasswordRequest(it.email))
            }
        }
    }

    private fun resetPassword(request: ResetPasswordRequest){
        val disposable = resetPasswordUseCase.execute(request)
            .doOnSubscribe { resetPasswordState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { resetPasswordState.value = LoadState.Data(it) },
                onError = { resetPasswordState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }
}