package com.wstik.kinde.utils

import com.google.android.gms.tasks.Task
import io.reactivex.Completable

fun <TResult> Task<TResult>.toCompletable(): Completable {
    return Completable.create { emitter ->
        this.addOnSuccessListener { emitter.onComplete() }
            .addOnFailureListener { emitter.tryOnError(it) }
    }
}