package com.wstik.kinde.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.UserProfile
import com.wstik.kinde.domain.GetUserProfileUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ProfileViewModel(getUserProfileUseCase: GetUserProfileUseCase) : ViewModel() {

    val profileState = MutableLiveData<LoadState<UserProfile>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        val disposable = getUserProfileUseCase.execute()
            .doOnSubscribe { profileState.value = LoadState.Loading }
            .subscribeBy(
                onNext = { profileState.value = LoadState.Data(it) },
                onError = { profileState.value = LoadState.Error(it) }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
