package com.inheritex.demo.api

import io.reactivex.observers.DisposableObserver
import org.json.JSONObject
import retrofit2.HttpException

/// [ResponseListner] This class is used for response listener
abstract class ResponseListner<T> : DisposableObserver<T>() {
    override fun onComplete() {
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            when (e.code()) {
                400 -> {
                    val response = e.response()
                    val errorJson = JSONObject(response!!.errorBody()!!.string())
                    onFailure(Throwable(errorJson.getString("error")))
                    return
                }
            }

        }
        onFailure(e)
    }

    /// [onSuccess] This method is use for success call
    abstract fun onSuccess(response: T?)

    /// [onFailure] This method is use for onFailure call
    abstract fun onFailure(error: Throwable?)
}