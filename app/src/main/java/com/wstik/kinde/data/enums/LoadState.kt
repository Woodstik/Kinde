package com.wstik.kinde.data.enums

sealed class LoadState<in T> {
    data class Error(val throwable: Throwable) : LoadState<Any?>()
    object Loading : LoadState<Any?>()
    data class Data<T>(val data: T) : LoadState<T>()
}
