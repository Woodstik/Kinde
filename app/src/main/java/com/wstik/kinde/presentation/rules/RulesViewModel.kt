package com.wstik.kinde.presentation.rules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.RulesForm
import com.wstik.kinde.data.requests.AgreeRulesRequest
import com.wstik.kinde.domain.AgreeRulesUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class RulesViewModel(private val agreeRulesUseCase: AgreeRulesUseCase) : ViewModel() {

    val agreeState = MutableLiveData<LoadState<Unit>>()
    val formState = MutableLiveData<RulesForm>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun agreeRules() {
        formState.value?.let { form ->
            val disposable = agreeRulesUseCase.execute(AgreeRulesRequest(form.signature))
                .doOnSubscribe { agreeState.value = LoadState.Loading }
                .subscribeBy(
                    onNext = { agreeState.value = LoadState.Data(it) },
                    onError = { agreeState.value = LoadState.Error(it) }
                )
            compositeDisposable.add(disposable)
        }
    }
}