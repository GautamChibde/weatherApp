package com.chibde.weatherapp.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 ref = https://stackoverflow.com/a/50473752/5164673
 */

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
            // optional, handle coroutine cancellation however you'd like here
        }
    }

    this.addOnSuccessListener { result -> deferred.complete(result) }
    this.addOnFailureListener { exception -> deferred.completeExceptionally(exception) }

    return deferred
}